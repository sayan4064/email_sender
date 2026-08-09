document.getElementById('emailForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const to = document.getElementById('to').value;
    const subject = document.getElementById('subject').value;
    const body = document.getElementById('body').value;
    
    const sendBtn = document.getElementById('sendBtn');
    const messageDiv = document.getElementById('responseMessage');

    // Show loading state
    sendBtn.disabled = true;
    sendBtn.textContent = 'Sending...';
    messageDiv.className = 'message info';
    messageDiv.textContent = 'Sending email, please wait...';

    try {
        const response = await fetch('/api/email/send', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ to, subject, body })
        });

        const data = await response.json();

        if (response.ok) {
            // Success
            messageDiv.className = 'message success';
            messageDiv.textContent = data.message || 'Email sent successfully!';
            document.getElementById('emailForm').reset();
        } else {
            // Error from server
            messageDiv.className = 'message error';
            messageDiv.textContent = data.error || 'Error: Failed to send email.';
        }
    } catch (error) {
        // Network Error
        messageDiv.className = 'message error';
        messageDiv.textContent = 'Network error: Could not reach the server.';
    } finally {
        // Reset button state
        sendBtn.disabled = false;
        sendBtn.textContent = 'Send Email';
    }
});
