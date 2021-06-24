package client.utility;

import common.content.*;

import java.util.Scanner;

public class ElementReader {

    public SpaceMarine readElement(Scanner sc) throws IllegalArgumentException {
        String argument;
        argument = sc.nextLine().trim();
        if (argument.equals("")) {
            throw new IllegalArgumentException("Name cannot be empty word.");
        }
        String nameSM = argument;

        argument = sc.nextLine().trim();
        try {
            Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinate X value must be integer.");
        }

        int xSM = Integer.parseInt(argument);

        argument = sc.nextLine().trim();
        try {
            Integer y = Integer.parseInt(argument);
            if (y > 941) {
                throw new IllegalArgumentException("Coordinate Y max value: 941.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coordinate Y value must be integer.");
        }
        Integer ySM = Integer.parseInt(argument);

        argument = sc.nextLine().trim();
        if (!argument.equals("")) {
            try {
                Integer health = Integer.parseInt(argument);
                if (health < 0) {
                    throw new IllegalArgumentException("Health value must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Health value must be integer or empty word.");
            }
        }
        Integer healthSM;
        if (argument.equals("")) {
            healthSM = null;
        } else {
            healthSM = Integer.parseInt(argument);
        }

        argument = sc.nextLine().trim();
        if (!argument.equals("")) {
            try {
                AstartesCategory.valueOf(argument);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Possible values: ASSAULT, TACTICAL, CHAPLAIN. Or empty word.");
            }
        }
        AstartesCategory acSM;
        if (argument.equals("")) {
            acSM = null;
        } else {
            acSM = AstartesCategory.valueOf(argument);
        }

        argument = sc.nextLine().trim();
        if (!argument.equals("")) {
            try {
                Weapon.valueOf(argument);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Possible values: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER. Or empty word.");
            }
        }
        Weapon weaponSM;
        if (argument.equals("")) {
            weaponSM = null;
        } else {
            weaponSM = Weapon.valueOf(argument);
        }

        argument = sc.nextLine().trim();
        if (!argument.equals("")) {
            try {
                MeleeWeapon.valueOf(argument);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Possible values: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Or empty word.");
            }
        }
        MeleeWeapon meleeSM;
        if (argument.equals("")) {
            meleeSM = null;
        } else {
            meleeSM = MeleeWeapon.valueOf(argument);
        }

        argument = sc.nextLine().trim();
        if (argument.equals("")) {
            throw new IllegalArgumentException("Chapter name cannot be empty word.");
        }
        String cNameSM = argument;

        argument = sc.nextLine().trim();
        if (argument.equals("")) {
            throw new IllegalArgumentException("Chapter world cannot be empty word.");
        }
        String cWorldSM = argument;
        return new SpaceMarine(nameSM, new Coordinates(xSM, ySM), healthSM, acSM, weaponSM, meleeSM, new Chapter(cNameSM, cWorldSM));
    }
}
