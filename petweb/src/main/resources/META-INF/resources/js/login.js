document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita o envio padrão do formulário

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const loginData = {
        email: email,
        password: password
    };

    fetch('/login/select', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
            if (response.ok) {
            alert('Usuário Logado com Sucesso!');
                return response.json();
            } else {
                return response.json().then(data => {
                    throw new Error(data.message || 'Usuário ou Senha Inválidos!');
                });
            }
        })
        .then(data => {
            // Armazena os dados do usuário no sessionStorage
            sessionStorage.setItem('user', JSON.stringify(data));

            // Redireciona para a página de atualização
            window.location.href = data.redirect;
        })
        .catch(error => {
            console.error('Erro:', error);
            alert(error.message);
        });
    });
    document.getElementById('showPassword').addEventListener('change', function() {
                                const passwordField = document.getElementById('password');
                                if (this.checked) {
                                    passwordField.type = 'text';
                                } else {
                                    passwordField.type = 'password';
                                }
                            });