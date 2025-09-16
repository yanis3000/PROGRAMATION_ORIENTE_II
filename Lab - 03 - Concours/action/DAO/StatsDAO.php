<?php

    class StatsDAO {

        public static function save($name, $roomType) {
        $user = [];

            //if (!isset($name) && !isset($roomType)) {
                $user = [
                    "name" => $name,
                    "roomType" => $roomType,
                    "visibility" => 1
                ];
            //}
            
            $myfile = fopen("action/DAO/enregistrement.txt", "a");
            $userWrite = $user['name'] . ";" . $user['roomType'] . ";" . "\n";
            $result = fwrite($myfile, $userWrite);
            fclose($myfile);
            return $result;
        }

    }