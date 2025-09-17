<?php
    require_once("action/CommonAction.php");
    require_once("action/DAO/StatsDAO.php");
    
    class IndexAction extends CommonAction {

        public function __construct() {
            parent::__construct(CommonAction::$VISIBILITY_PUBLIC);
        }

        protected function executeAction() {

            if (!empty($_POST["name"]) && !empty($_POST["type"])) {
                StatsDAO::save($_SESSION["name"], $_SESSION["type"]);            
            }

            return [];
        }
    }