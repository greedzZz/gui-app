package client.utility;

import common.content.SpaceMarine;

public class SpaceMarineDescriber {
    public String describe(SpaceMarine sm, Localizator localizator) {
        return "id: " + sm.getID() + "\n" +
                localizator.getKeyString("Name") + ": " + sm.getName() + "\n" +
                "X: " + sm.getCoordinateX() + "\n" +
                "Y: " + sm.getCoordinateY() + "\n" +
                localizator.getKeyString("CreationDate") + ": " + localizator.getDate(sm.getCreationDateValue()) + "\n" +
                localizator.getKeyString("Health") + ": " + sm.getHealth() + "\n" +
                localizator.getKeyString("AstartesCategory") + ": " + sm.getCategory() + "\n" +
                localizator.getKeyString("Weapon") + ": " + sm.getWeaponType() + "\n" +
                localizator.getKeyString("MeleeWeapon") + ": " + sm.getMeleeWeapon() + "\n" +
                localizator.getKeyString("ChapterName") + ": " + sm.getChapterName() + "\n" +
                localizator.getKeyString("ChapterWorld") + ": " + sm.getChapterWorld() + "\n" +
                localizator.getKeyString("Owner") + ": " + sm.getOwner();
    }
}
