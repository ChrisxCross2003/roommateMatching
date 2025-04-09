async function findGroup() {
    const inputId = document.getElementById('studentIdInput').value.trim().toLowerCase();
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = ''; // Clear previous results

    try {
        // Add cache busting to always fetch the latest version
        const response = await fetch(`matches.json?cacheBust=${Date.now()}`);
        const data = await response.json();

        // Check if the student is in the odd-men-out list
        const oddManOut = (data.oddMenOut || []).find(student =>
            student.id.toLowerCase() === inputId
        );

        if (oddManOut) {
            resultDiv.innerHTML = `
                <p style="color:red;">We're sorry, we could not find any compatible matches, but we will work hard to match you in the next phase!</p>
            `;
            return;
        }

        // Find the group the student belongs to
        const group = data.groupResults.find(group =>
            group.students.some(student =>
                student.id.toLowerCase() === inputId
            )
        );

        if (!group) {
            resultDiv.innerHTML = `<p style="color:red;">No group found for ID "${inputId}".</p>`;
            return;
        }

        // Build the result display
        let output = `<h2>Group ${group.groupID}</h2><ul>`;

        group.students.forEach(student => {
            output += `<li><strong>${student.name}</strong> (${student.id})</li>`;
        });

        output += `</ul><p><strong>Overall Compatibility:</strong> ${group.overallCompatibility}</p>`;

        group.compatibilityScores.forEach((score, index) => {
            if (score !== "N/A" && index + 1 < group.students.length) {
                output += `<p>S${index + 1} → S${index + 2} Compatibility: ${score}</p>`;
            }
        });

        resultDiv.innerHTML = output;

    } catch (err) {
        resultDiv.innerHTML = `<p style="color:red;">Error loading group data.</p>`;
        console.error('Error fetching or processing JSON:', err);
    }
}