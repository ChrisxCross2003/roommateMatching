async function findGroup() {
    const inputId = document.getElementById('studentIdInput').value.trim().toLowerCase();
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = ''; // Clear any previous results

    try {
        // Fetch the JSON file (make sure it's accessible from the correct URL)
        const response = await fetch('matches.json');
        const data = await response.json();

        // Check if the student is in the oddMenOut list
        const oddManOut = data.oddMenOut.find(student =>
            student.id.toLowerCase() === inputId
        );

        if (oddManOut) {
            resultDiv.innerHTML = `
                <p style="color:red;">We're sorry, we could not find any compatible matches, but we will work hard to match you in the next phase!</p>
            `;
            return; // Stop the function here if the student is in the odd-man-out list
        }

        // Search for the student's group based on their ID
        const group = data.groupResults.find(group =>
            group.students.some(student =>
                student.id.toLowerCase() === inputId
            )
        );

        if (!group) {
            resultDiv.innerHTML = `<p style="color:red;">No group found for ID "${inputId}".</p>`;
            return;
        }

        // Build HTML output for the group
        let output = `<h2>Group ${group.groupID}</h2><ul>`;

        group.students.forEach(student => {
            output += `<li><strong>${student.name}</strong> (${student.id})</li>`;
        });

        output += `</ul><p><strong>Overall Compatibility:</strong> ${group.overallCompatibility}</p>`;

        // Loop through the compatibility scores dynamically
        group.compatibilityScores.forEach((score, index) => {
            if (score !== "N/A") {
                output += `<p>S${index + 1} → S${index + 2} Compatibility: ${score}</p>`;
            }
        });

        resultDiv.innerHTML = output;

    } catch (err) {
        resultDiv.innerHTML = `<p style="color:red;">Error loading group data.</p>`;
        console.error(err);
    }
}