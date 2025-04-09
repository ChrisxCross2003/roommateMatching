async function findGroup() {
    const inputId = document.getElementById('studentIdInput').value.trim().toLowerCase();
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = ''; // Clear previous results

    try {
        // Add cache busting to always fetch the latest version
        const response = await fetch(`matches_8b5f1a2d7a9c3f1e3d0.json?cacheBust=${Date.now()}`);
        const data = await response.json();

        // Check if the student is in the odd-men-out list
        const oddManOut = (data.oddMenOut || []).find(student =>
            student.id.toLowerCase() === inputId
        );

        if (oddManOut) {
            resultDiv.innerHTML = `
                <p style="color:red;">We're so sorry, but you are an Odd-Man-Out :(. We could not find any compatible matches, but you have first priority in the next matching phase!</p>
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