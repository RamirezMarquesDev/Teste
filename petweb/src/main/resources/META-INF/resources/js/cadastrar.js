document.getElementById('userForm').addEventListener('submit', function(event) {

    var nome = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var senha = document.getElementById('password').value;

    // Verifica se todos os campos estão preenchidos
    if (!nome || !email || !senha) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    // Se chegamos até aqui, o JavaScript está funcionando, então podemos prevenir a ação padrão do formulário
    event.preventDefault();

    var dadosUsuario = {
        name: nome,
        email: email,
        password: senha
    };

    fetch('/cadastrar/persist', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dadosUsuario)
    })
    .then(response => {
        if (response.status === 201) {
        alert('Usuário Cadastrado com Sucesso!');
            window.location.href = response.headers.get('Location');
        } else if (response.status === 400) {
            response.text().then(message => alert(message));
        } else {
            return response.json();
        }
    })
    .then(data => {
        if (data) {
            console.log(data);
        }
    })
    .catch((error) => {
        console.error('Error:', error);
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
