package client.bundles;

import java.util.ListResourceBundle;

public class gui_sq_AL extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AuthTitle", "Autorizimi"},
            {"LoginField", "Identifikohu"},
            {"PasswordField", "Fjalëkalimi"},
            {"SignUpButton", "Regjistrohu"},
            {"LanguageLabel", "Gjuhe:"},
            {"UserLabel", "Përdoruesi:"},
            {"TableTab", "Tabela"},
            {"VisualTab", "Vizualizimi"},
            {"Owner", "Pronari"},
            {"Name", "Emri"},
            {"CreationDate", "Data e krijimit"},
            {"Health", "Shëndeti"},
            {"AstartesCategory", "Kategoria Astartes"},
            {"Weapon", "Armë"},
            {"MeleeWeapon", "Arma e përleshje"},
            {"ChapterName", "Emri i kapitullit"},
            {"ChapterWorld", "Bota e Kapitullit"},
            {"Help", "Ndihmë"},
            {"ExecuteScript", "Ekzekutoni shkrimin"},
            {"Info", "Info"},
            {"Insert", "Fut"},
            {"Update", "Update"},
            {"FilterByChapter", "Filter nga kapitulli"},
            {"Clear", "Qartë"},
            {"FilterStartsWithName", "Filtri fillon me emrin"},
            {"RemoveKey", "Hiq çelësin"},
            {"RemoveGreaterKey", "Hiq çelësin më të madh"},
            {"RemoveGreater", "Hiqni më shumë"},
            {"ReplaceIfGreater", "Zëvendësoni nëse është më i madh"},
            {"GroupCountingByCoordinates", "Numërimi i grupit nga koordinatat"},
            {"CancelButton", "Anulo"},
            {"EditTitle", "Redaktimi"},
            {"Information", "Info"},
            {"Error", "Gabim"},
            {"RegisterSuccess", "Regjistrimi përfundoi me sukses!"},
            {"SignUpError", "Një përdorues me këtë login është regjistruar tashmë."},
            {"SignInError", "Na vjen keq, login / fjalëkalimi është i pasaktë."},
            {"SignInEmpty", "Identifikohu / fjalëkalimi nuk mund të jetë fjalë bosh."},
            {"EditError", ", {1}, {3}, {4}, {5} Vlerat nuk mund të jenë fjalë boshe."},
            {"DialogKey", "Celës:"},
            {"DialogName", "Emri:"},
            {"DialogWorld", "Botë:"},
            {"RefreshFailed", "Dështoi në përditësimin e tabelës për shkak të çështjeve të serverëve."},
            {"RefreshLost", "Lost lidhje me serverin."},
            {"UnavailableError", "Për fat të keq, serveri aktualisht është i padisponueshëm."},
            {"HelpResult", "group_counting_by_coordinates: Grupet e elementeve të mbledhjes nga vlera e fushës së koordinatave,\nshfaqin numrin e elementeve në secilin grup. \n" +
                    "update \"id\": përditëson vlerën e elementit të grumbullimit, ID-ja e të cilit është e barabartë me të dhënë. \n" +
                    "clear: Pastron koleksionin. \n" +
                    "remove_greater: Heq të gjitha sendet nga koleksioni që janë më të mëdha se ato të specifikuara. \n" +
                    "help: Shfaq informacion mbi komandat në dispozicion. \n" +
                    "filter_starts_with_name \"name\": Shfaq elemente vlera e fushës së të cilëve fillon me një substring të dhënë. \n" +
                    "execute_script \"file_name\": Lexon dhe ekzekuton një skript nga skedari i specifikuar. \n" +
                    "remove_key \"key\": Heq një element të mbledhjes nga çelësi i tij. \n" +
                    "insert \"key\": shton një element të ri me çelësin e dhënë. \n" +
                    "remove_greater_key \"key\": Heq nga mbledhja Të gjitha elementet, çelësi i të cilave e tejkalon atë. \n" +
                    "filter_by_chapter \"chapter\": Shfaq elementet fusha e të cilëve është e barabartë me të dhënë. \n" +
                    "replace_if_greater \"key\": Zëvendëson vlerën me çelësi nëse vlera e re është më e madhe se ajo e vjetër. \n" +
                    "info: tregon informacion rreth mbledhjes. "},
            {"InfoResult", "Lloji i grumbullimit: {0}\nMbledhja e inicializimit Data: {1}\nMadhësia e grumbullimit: {2}"},
            {"InsertSuc", "Artikulli është shtuar me sukses në koleksion!"},
            {"InsertErr", "Dështoi në shtimin e sendit në koleksion."},
            {"UpdateSuc", "Vlera e artikullit përditësuar me sukses."},
            {"UpdateErr", "Dështoi në përditësimin e vlerës së artikullit."},
            {"RemoveKeySuc", "Artikulli me çelësin e dhënë është fshirë me sukses."},
            {"RemoveKeyErr", "Dështoi në fshirjen e sendit me çelës të specifikuar."},
            {"ClearSuc", "Mbledhja është pastruar me sukses."},
            {"ClearErr", "Dështoi në pastrimin e koleksionit."},
            {"RemoveGreaterSuc", "Të gjitha sendet më të mëdha janë hequr me sukses."},
            {"RemoveGreaterErr", "Dështoi në fshirjen e artikujve më të mëdhenj."},
            {"RemoveGreaterKeySuc", "Të gjitha sendet me çelësa më të mëdhenj u fshinë me sukses."},
            {"RemoveGreaterKeyErr", "Dështoi në fshirjen e artikujve me çelësa më të mëdhenj."},
            {"ReplaceIfGreaterSuc", "Artikulli u zëvendësua me sukses."},
            {"ReplaceIfGreaterErr", "Dështoi në zëvendësimin e sendit."},
            {"GroupCountingByCoordinatesResult", "Ka {0} hapësirë ​​të hapësirës në tremujorin e parë të koordinatave, në të dytin, {2} në të tretin, {3} në të katërtin."},
            {"FilterByChapterResult", "Elemente vlera e të cilave është e barabartë me vlerën e futur:\n"},
            {"FilterStartsWithNameResult", "Elementet e të cilëve fillon me vlerën e futur:\n"},
            {"ScriptSuccess", "Skenari është ekzekutuar me sukses."},
            {"ScriptRunTime", "Ka ndodhur një gabim gjatë ekzekutimit të skriptit."},
            {"ScriptFile", "Dështoi në leximin e skedarit."},
            {"DataBaseError", "Ka ndodhur një gabim i qasjes së bazës së të dhënave ose lidhja është mbyllur."},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
