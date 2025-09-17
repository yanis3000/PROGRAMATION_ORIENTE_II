<?php

    class StatsDAO {

        public static function save($name, $roomType) {
        $user = [];

            // On pourrait faire une boucle foreach de tous les elems pour voir s'il y a des copies de noms

            if (isset($name)) {
                $user = [
                    "name" => $name,
                    "roomType" => $roomType
                ];

                $myfile = fopen("action/DAO/enregistrement.txt", "a");
                $userWrite = $user['name'] . ";" . $user['roomType'] . ";" . "\n";
                $result = fwrite($myfile, $userWrite);
                fclose($myfile);
                return $result;
            }
            
        }

        // public static function count() {

        // }

        // public static function countPerRoomType() {


        // }

    }