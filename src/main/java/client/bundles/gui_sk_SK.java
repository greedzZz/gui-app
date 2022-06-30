package client.bundles;

import java.util.ListResourceBundle;

public class gui_sk_SK extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AuthTitle", "Autorizácia"},
            {"LoginField", "Prihlásiť sa"},
            {"PasswordField", "Heslo"},
            {"SignUpButton", "Prihlásiť Se"},
            {"LanguageLabel", "Jazyk:"},
            {"UserLabel", "Užívateľ:"},
            {"TableTab", "Tabuľka"},
            {"VisualTab", "Vizualizácia"},
            {"Owner", "Majiteľ"},
            {"Name", "Názov"},
            {"CreationDate", "Dátum vytvorenia"},
            {"Health", "Zdravie"},
            {"AstartesCategory", "Kategória Astartes"},
            {"Weapon", "Zbraň"},
            {"MeleeWeapon", "Melee Weapon"},
            {"ChapterName", "Názov kapitoly"},
            {"ChapterWorld", "Kapitolový svet"},
            {"Help", "Pomoc"},
            {"ExecuteScript", "Execute Script"},
            {"Info", "Info"},
            {"Insert", "Vložiť"},
            {"Update", "Aktualizácia"},
            {"FilterByChapter", "Filter podľa kapitoly"},
            {"Clear", "Jasný"},
            {"FilterStartsWithName", "Filter začína s menom"},
            {"RemoveKey", "Odstrániť kľúč"},
            {"RemoveGreaterKey", "Odstráňte väčší kľúč"},
            {"RemoveGreater", "Odstrániť väčšie"},
            {"ReplaceIfGreater", "Nahradiť, ak je väčšie"},
            {"GroupCountingByCoordinates", "Skupina počítanie podľa súradníc"},
            {"CancelButton", "Zrušiť"},
            {"EditTitle", "Úprava"},
            {"Information", "Info"},
            {"Error", "Chyba"},
            {"RegisterSuccess", "Registrácia bola úspešne dokončená!"},
            {"SignUpError", "Užívateľ s týmto prihlásením je už zaregistrovaný."},
            {"SignInError", "Prepáčte, prihlasovacie / heslo je nesprávne."},
            {"SignInEmpty", "Prihlásenie / heslo nemôže byť prázdne slovo."},
            {"EditError", "{0}, {1}, {2}, {3}, {4}, {5} hodnoty nemôžu byť prázdne slová."},
            {"DialogKey", "Kľúč:"},
            {"DialogName", "Názov:"},
            {"DialogWorld", "Svet:"},
            {"RefreshFailed", "Nepodarilo sa aktualizovať tabuľku z dôvodu problémov servera."},
            {"RefreshLost", "Stratené pripojenie k serveru."},
            {"UnavailableError", "Bohužiaľ, server je momentálne nedostupný."},
            {"HelpResult", "group_counting_by_coordinates: Skupiny Zbierka zberu podľa hodnoty súradníc,\nzobrazí počet prvkov v každej skupine. \n" +
                    "update \"id\": Aktualizuje hodnotu zberného prvku, ktorého ID sa rovná danej. \n" +
                    "clear: Vymaže zber. \n" +
                    "remove_greater: Odstráni všetky položky z kolekcie, ktoré sú väčšie ako zadané. \n" +
                    "help: Zobrazí informácie o dostupných príkazoch. \n" +
                    "filter_starts_with_name \"name\": Zobrazí prvky, ktorých názov poľa Názov začína daným podreťazcom. \n" +
                    "execute_script \"file_name\": číta a vykoná skript zo zadaného súboru. \n" +
                    "remove_key \"key\": Odstráni zberný prvok jeho kľúčom. \n" +
                    "insert \"key\": Pridá nový prvok s daným kľúčom. \n" +
                    "remove_greater_key \"key\": Odstráni z kolekcie Všetky prvky, ktorých kľúč presahuje daný. \n" +
                    "filter_by_chapter \"chapter\": Zobrazuje prvky, ktorých pole kapitoly sa rovná danej. \n" +
                    "replace_if_greater \"key\": Nahrádza hodnotu kľúčom, ak je nová hodnota väčšia ako tá stará. \n" +
                    "info: Zobrazí informácie o kolekcii. "},
            {"InfoResult", "Typ zberu: {0}\nDátum zberu Dátum: {1}\nZbierka Veľkosť: {2}"},
            {"InsertSuc", "Položka bola úspešne pridaná do kolekcie!"},
            {"InsertErr", "Nepodarilo sa pridať položku do kolekcie."},
            {"UpdateSuc", "Položka bola úspešne aktualizovaná."},
            {"UpdateErr", "Nepodarilo sa aktualizovať hodnotu položky."},
            {"RemoveKeySuc", "Položka s daným kľúčom bola úspešne odstránená."},
            {"RemoveKeyErr", "Nepodarilo sa odstrániť položku so zadaným kľúčom."},
            {"ClearSuc", "Zbierka bola úspešne vymazaná."},
            {"ClearErr", "Nepodarilo sa odstrániť zber."},
            {"RemoveGreaterSuc", "Všetky väčšie položky boli úspešne odstránené."},
            {"RemoveGreaterErr", "Nepodarilo sa odstrániť väčšie položky."},
            {"RemoveGreaterKeySuc", "Všetky položky s väčšími kľúčmi boli úspešne odstránené."},
            {"RemoveGreaterKeyErr", "Nepodarilo sa odstrániť položky s vyššími kľúčmi."},
            {"ReplaceIfGreaterSuc", "Položka bola úspešne nahradená."},
            {"ReplaceIfGreaterErr", "Nepodarilo sa nahradiť položku."},
            {"GroupCountingByCoordinatesResult", "V prvej štvrtine súradníc, {1} v druhej, {2} v treťom, {3} v štvrtej štvrti."},
            {"FilterByChapterResult", "Prvky, ktorých hodnota kapitoly sa rovná zadanej hodnote:\n"},
            {"FilterStartsWithNameResult", "Prvky, ktorých začína zadanou hodnotou:\n"},
            {"ScriptSuccess", "Skript bol úspešne vykonaný."},
            {"ScriptRunTime", "Pri spustení skriptu sa vyskytla chyba."},
            {"ScriptFile", "Nepodarilo sa čítať súbor."},
            {"DataBaseError", "Vyskytla sa chyba prístupu databázy alebo sa pripojenie uzavrelo."},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
