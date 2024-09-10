document.addEventListener('DOMContentLoaded', function() {
    // Recupera os dados do usuário do sessionStorage
    const user = JSON.parse(sessionStorage.getItem('user'));

    if (user) {
        // Preenche os campos do formulário com os dados do usuário
        document.getElementById('name').value = user.name;
        document.getElementById('email').value = user.email;
        document.getElementById('password').value = user.password; // Certifique-se de que a senha é enviada ao frontend (segurança!)
        document.getElementById('userId').value = user.userId;
    } else {
        console.error('Dados do usuário não encontrados no sessionStorage');
    }

    // Adiciona o event listener ao checkbox para mostrar/ocultar a senha
    document.getElementById('showPassword').addEventListener('change', function() {
        const passwordField = document.getElementById('password');
        if (this.checked) {
            passwordField.type = 'text';
        } else {
            passwordField.type = 'password';
        }
    });

    // Adiciona o event listener ao botão editar para enviar os dados atualizados
    document.getElementById('editButton').addEventListener('click', function() {
        const updatedUserData = {
            userId: document.getElementById('userId').value,
            name: document.getElementById('name').value,
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        };

        fetch('/update/edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedUserData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.message === 'Usuário atualizado com sucesso') {
                alert('Usuário atualizado com sucesso');
                // Atualiza os dados no sessionStorage
                sessionStorage.setItem('user', JSON.stringify(updatedUserData));
                window.location.href = '/home';
            } else {
                alert('Erro ao atualizar o usuário: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao atualizar o usuário');
        });
    });
    document.getElementById('deleteButton').addEventListener('click', function() {
        const email = document.getElementById('email').value;

        fetch('/update/delete/'+ email, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            if (data.message === 'Usuário deletado com sucesso') {
                alert('Usuário deletado com sucesso');
                // Limpa os dados do sessionStorage
                sessionStorage.removeItem('user');
                // Redireciona para a página inicial
                window.location.href = '/home';
            } else {
                alert('Erro ao excluir o usuário: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao excluir o usuário');
        });
    });
});


