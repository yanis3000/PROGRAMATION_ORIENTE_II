<?php
require_once("action/IndexAction.php");

$action = new IndexAction();
$data = $action->execute();

// Si la session contient déjà un nom, empêcher la réinscription et afficher un message
if (isset($_SESSION["name"])) {
    $alreadyRegistered = true;
} else {
    $alreadyRegistered = false;
    if (!empty($_POST["name"])) {
        $_SESSION["name"] = $_POST["name"]; // Sauvegarde du nom dans la session
    }
}

require_once("partial/header.php");
?>

<h1>Gagner une nuitée au château Frontenac</h1>
<div class="half-width">
    <?php
    if ($alreadyRegistered) {
        // Message d'erreur si l'utilisateur est déjà inscrit
        echo "<p>Vous êtes déjà inscrit au concours. Vous ne pouvez pas vous réinscrire.</p>";
    } else {
        // Affichage du formulaire si l'utilisateur n'est pas inscrit
        ?>
        <form action="" method="post">
            <div>
                <input type="text" name="name" placeholder="Nom complet" required>
            </div>
            <div>
                <select name="type" required>
                    <option value="">Type de chambre souhaité</option>
                    <option>Simple</option>
                    <option>Double</option>
                    <option>Suite</option>
                </select>
            </div>
            <div>
                <button>Confirmer l'inscription</button>
            </div>
        </form>
        <?php
    }
    ?>
</div>

<?php
require_once("partial/footer.php");
?>
