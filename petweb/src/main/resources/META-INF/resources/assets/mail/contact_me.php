<?php
// Verifica se os dados foram enviados via POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Recebe os dados do formulário
    $name = strip_tags(htmlspecialchars($_POST['name']));
    $email_address = strip_tags(htmlspecialchars($_POST['email']));
    $phone = strip_tags(htmlspecialchars($_POST['phone']));
    $message = strip_tags(htmlspecialchars($_POST['message']));

    // Verifica se todos os campos estão preenchidos
    if (!empty($name) && !empty($email_address) && !empty($phone) && !empty($message)) {
        // Prepara os dados para serem enviados como JSON
        $data = array(
            'name' => $name,
            'email' => $email_address,
            'phone' => $phone,
            'message' => $message
        );

        // Converte os dados para JSON
        $json_data = json_encode($data);

        // Envia os dados para o backend usando uma solicitação HTTP POST
        $url = 'http://localhost:8080/api/send-mail';
        $ch = curl_init($url);

        // Configura as opções da solicitação HTTP
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $json_data);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json'));

        // Envia a solicitação HTTP e obtém a resposta
        $response = curl_exec($ch);

        // Fecha a solicitação HTTP
        curl_close($ch);

        // Exibe a resposta
        echo $response;
    } else {
        // Caso algum campo esteja vazio, exibe uma mensagem de erro
        echo "Todos os campos do formulário devem ser preenchidos!";
    }
} else {
    // Caso a solicitação não seja do tipo POST, exibe uma mensagem de erro
    echo "Método de requisição inválido!";
}
?>
