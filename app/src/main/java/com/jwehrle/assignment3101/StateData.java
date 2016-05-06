package com.jwehrle.assignment3101;

import java.util.HashMap;

/**
 * Created by jwehrle on 4/27/16.
 * State data to load if the state_table is empty. I know there are better ways but this seemed
 * simplest for the purposes of an example.
 * This class is a singleton because I don't want to fill the map more than once.
 * All images are from Wikipedia and are in the public commons.
 */
public class StateData {

    private static HashMap<String, String> stateAnimalsMap;

    public static HashMap<String, String> getStateAnimalsMap() {
        if (stateAnimalsMap == null) {
            initMap();
        }
        return stateAnimalsMap;
    }

    private static void initMap() {
        if (stateAnimalsMap == null) {
            stateAnimalsMap = new HashMap<>();
            fillMap();
        }
    }

    private static void fillMap() {
        if (stateAnimalsMap != null) {
            stateAnimalsMap.put("Alabama", "https://upload.wikimedia.org/wikipedia/commons/1/14/A_Florida_Black_Bear.jpg");
            stateAnimalsMap.put("Alaska", "https://upload.wikimedia.org/wikipedia/commons/4/47/Bigbullmoose.jpg");
            stateAnimalsMap.put("Arizona", "https://upload.wikimedia.org/wikipedia/commons/b/ba/Squaw-ringtail-28073.jpg");
            stateAnimalsMap.put("Arkansas", "https://upload.wikimedia.org/wikipedia/commons/0/0d/Quivira-Whitetail-Buck.jpg");
            stateAnimalsMap.put("California", "https://upload.wikimedia.org/wikipedia/commons/d/de/Monarch_the_bear.jpg");
            stateAnimalsMap.put("Colorado", "https://upload.wikimedia.org/wikipedia/commons/f/f2/Bighorn_pair.jpg");
            stateAnimalsMap.put("Connecticut", "https://upload.wikimedia.org/wikipedia/commons/b/b8/Sperm_whale_pod.jpg");
            stateAnimalsMap.put("Delaware", "https://upload.wikimedia.org/wikipedia/commons/0/0f/Urocyon_cinereoargenteus_Back_Bay_National_Wildlife_Refuge.jpg");
            stateAnimalsMap.put("Florida", "https://upload.wikimedia.org/wikipedia/commons/d/d7/Puma_concolor_coryi_cropped.jpg");
            stateAnimalsMap.put("Georgia", "https://upload.wikimedia.org/wikipedia/commons/c/cc/White-tailed_Deer-27527-3.jpg");
            stateAnimalsMap.put("Hawaii", "https://upload.wikimedia.org/wikipedia/commons/8/83/Monachus_schauinslandi.jpg");
            stateAnimalsMap.put("Idaho", "https://upload.wikimedia.org/wikipedia/commons/d/d2/Appaloosa46-2.jpg");
            stateAnimalsMap.put("Illinois", "https://upload.wikimedia.org/wikipedia/commons/b/b7/White-tailed_deer.jpg");
            stateAnimalsMap.put("Indiana", "https://upload.wikimedia.org/wikipedia/commons/8/83/Northern_Cardinal_Male-27527.jpg");
            stateAnimalsMap.put("Iowa", "https://upload.wikimedia.org/wikipedia/commons/d/d0/Carduelis-tristis-002.jpg");
            stateAnimalsMap.put("Kansas", "https://upload.wikimedia.org/wikipedia/commons/a/a8/Bison_Bull_in_Nebraska.jpg");
            stateAnimalsMap.put("Kentucky", "https://upload.wikimedia.org/wikipedia/commons/7/71/GroundSquirrelPosing.JPG");
            stateAnimalsMap.put("Louisiana", "https://upload.wikimedia.org/wikipedia/commons/6/63/Louisiana_black_bear.jpg");
            stateAnimalsMap.put("Maine", "https://upload.wikimedia.org/wikipedia/commons/e/e9/Lonesome-Lake-Moose.jpg");
            stateAnimalsMap.put("Maryland", "https://upload.wikimedia.org/wikipedia/commons/3/36/Applebite-Gentlemen.jpg");
            stateAnimalsMap.put("Massachusetts", "https://upload.wikimedia.org/wikipedia/commons/6/6a/Eubalaena_glacialis_with_calf.jpg");
            stateAnimalsMap.put("Michigan", "https://upload.wikimedia.org/wikipedia/commons/2/20/Deer_Night.jpg");
            stateAnimalsMap.put("Minnesota", "https://upload.wikimedia.org/wikipedia/commons/b/b0/Gavia_immer_-Minocqua%2C_Wisconsin%2C_USA_-swimming-8.jpg");
            stateAnimalsMap.put("Mississippi", "https://upload.wikimedia.org/wikipedia/commons/e/e4/Deer_running.jpg");
            stateAnimalsMap.put("Missouri", "https://upload.wikimedia.org/wikipedia/commons/0/08/NRCSDC01002_-_Flickr_-_USDAgov.jpg");
            stateAnimalsMap.put("Montana", "https://upload.wikimedia.org/wikipedia/commons/4/4a/Grizzly_bear_glacier_national_park_3.jpg");
            stateAnimalsMap.put("Nebraska", "https://en.wikipedia.org/wiki/File:White_tailed_deer_Nebraska.jpg");
            stateAnimalsMap.put("Nevada", "https://upload.wikimedia.org/wikipedia/commons/3/32/Ovis_canadensis_nelsoni_vof_1.jpg");
            stateAnimalsMap.put("New Hampshire", "https://upload.wikimedia.org/wikipedia/commons/2/2d/White-tailed_deer.JPG");
            stateAnimalsMap.put("New Jersey", "https://upload.wikimedia.org/wikipedia/commons/e/e9/Morgan_parked_out.jpg");
            stateAnimalsMap.put("New Mexico", "https://upload.wikimedia.org/wikipedia/commons/4/4f/Yellowstone-black-bear-07895.jpg");
            stateAnimalsMap.put("New York", "https://upload.wikimedia.org/wikipedia/commons/6/6b/American_Beaver.jpg");
            stateAnimalsMap.put("North Carolina", "https://en.wikipedia.org/wiki/File:Sciurus_carolinensis2.jpg");
            stateAnimalsMap.put("North Dakota", "https://upload.wikimedia.org/wikipedia/commons/d/de/Nokota_Horses_cropped.jpg");
            stateAnimalsMap.put("Ohio", "https://upload.wikimedia.org/wikipedia/commons/e/e1/White-tailed_deer%2C_Heath_Ohio.JPG");
            stateAnimalsMap.put("Oklahoma", "https://upload.wikimedia.org/wikipedia/commons/a/a0/Bison_bison_Wichita_Mountain_Oklahoma.jpg");
            stateAnimalsMap.put("Oregon", "https://upload.wikimedia.org/wikipedia/commons/4/49/Flickr_-_Oregon_Department_of_Fish_%26_Wildlife_-_North_American_Beaver.jpg");
            stateAnimalsMap.put("Pennsylvania", "https://upload.wikimedia.org/wikipedia/commons/9/9f/White-tailed_deer_fawn_in_Berwyn_PA.jpg");
            stateAnimalsMap.put("Rhode Island", "https://upload.wikimedia.org/wikipedia/commons/8/8d/Rhode_Island_Red.jpg");
            stateAnimalsMap.put("South Carolina", "https://upload.wikimedia.org/wikipedia/commons/c/cc/White-tailed_Deer-27527-3.jpg");
            stateAnimalsMap.put("South Dakota", "https://upload.wikimedia.org/wikipedia/commons/3/38/Canis_latrans_latrans_Pennington_County_SD.jpg");
            stateAnimalsMap.put("Tennessee", "https://upload.wikimedia.org/wikipedia/commons/b/b6/Racoon_Memphis_TN_2013-03-26_001.jpg");
            stateAnimalsMap.put("Texas", "https://upload.wikimedia.org/wikipedia/commons/5/5b/Armadillo1.jpg");
            stateAnimalsMap.put("Utah", "https://upload.wikimedia.org/wikipedia/commons/9/9d/Rocky-mountain-elk.jpg");
            stateAnimalsMap.put("Vermont", "https://upload.wikimedia.org/wikipedia/commons/6/67/BayMorgan.jpg");
            stateAnimalsMap.put("Virginia", "https://upload.wikimedia.org/wikipedia/commons/5/51/Virginia_big-eared_bat_female.JPG");
            stateAnimalsMap.put("Washington", "https://upload.wikimedia.org/wikipedia/commons/a/a2/OlympicMarmotImageFromNPSFlipped.jpg");
            stateAnimalsMap.put("West Virginia", "https://upload.wikimedia.org/wikipedia/commons/9/96/Black-bear-climbing-water_-_West_Virginia_-_ForestWander.jpg");
            stateAnimalsMap.put("Wisconsin", "https://upload.wikimedia.org/wikipedia/commons/5/57/AmericanBadger.JPG");
            stateAnimalsMap.put("Wyoming", "https://upload.wikimedia.org/wikipedia/commons/8/8d/American_bison_k5680-1.jpg");
        }
    }
}
