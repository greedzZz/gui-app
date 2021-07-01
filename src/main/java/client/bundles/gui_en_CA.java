package client.bundles;

import java.util.ListResourceBundle;

public class gui_en_CA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"AuthTitle", "Authorization"},
            {"LoginField", "Login"},
            {"PasswordField", "Password"},
            {"SignUpButton", "Sign Up"},
            {"LanguageLabel", "Language:"},
            {"UserLabel", "User:"},
            {"TableTab", "Table"},
            {"VisualTab", "Visualization"},
            {"Owner", "Owner"},
            {"Name", "Name"},
            {"CreationDate", "Creation date"},
            {"Health", "Health"},
            {"AstartesCategory", "Astartes category"},
            {"Weapon", "Weapon"},
            {"MeleeWeapon", "Melee weapon"},
            {"ChapterName", "Chapter name"},
            {"ChapterWorld", "Chapter world"},
            {"Help", "Help"},
            {"ExecuteScript", "Execute script"},
            {"Info", "Info"},
            {"Insert", "Insert"},
            {"Update", "Update"},
            {"FilterByChapter", "Filter by chapter"},
            {"Clear", "Clear"},
            {"FilterStartsWithName", "Filter starts with name"},
            {"RemoveKey", "Remove key"},
            {"RemoveGreaterKey", "Remove greater key"},
            {"RemoveGreater", "Remove greater"},
            {"ReplaceIfGreater", "Replace if greater"},
            {"GroupCountingByCoordinates", "Group counting by coordinates"},
            {"CancelButton", "Cancel"},
            {"EditTitle", "Editing"},
            {"Information", "Info"},
            {"Error", "Error"},
            {"RegisterSuccsess", "Registration completed successfully!"},
            {"SignUpError", "A user with this login is already registered."},
            {"SignInError", "Sorry, the login/password is incorrect."},
            {"SignInEmpty", "Login/password cannot be empty word."},
            {"EditError", "{0}, {1}, {2}, {3}, {4}, {5} values cannot be empty words."},
            {"DialogKey", "Key:"},
            {"DialogName", "Name:"},
            {"DialogWorld", "World:"},
            {"RefreshFailed", "Failed to update the table due to server issues."},
            {"RefreshLost", "Lost connection to server."},
            {"UnavailableError", "Unfortunately, the server is currently unavailable."},
            {"HelpResult", "group_counting_by_coordinates: Groups the elements of the collection by the value of the coordinates field,\ndisplay the number of elements in each group.\n" +
                    "update \"id\": Updates the value of the collection element whose id is equal to the given.\n" +
                    "clear: Clears the collection. \n" +
                    "remove_greater: Removes all items from the collection that are greater than the specified one. \n" +
                    "help: Displays information on available commands. \n" +
                    "filter_starts_with_name \"name\": Displays elements whose name field value begins with a given substring. \n" +
                    "execute_script \"file_name\": Reads and executes a script from the specified file. \n" +
                    "remove_key \"key\": Removes a collection element by its key. \n" +
                    "insert \"key\": Adds a new element with the given key. \n" +
                    "remove_greater_key \"key\": Removes from the collection all elements whose key exceeds the given one. \n" +
                    "filter_by_chapter \"chapter\": Displays elements whose chapter field is equal to the given. \n" +
                    "replace_if_greater \"key\": Replaces the value by key if the new value is greater than the old one. \n" +
                    "info: Displays information about the collection."},
            {"InfoResult", "Collection type: {0} \nCollection initialization date: {1} \nCollection size: {2}"},
            {"InsertSuc", "The item has been successfully added to the collection!"},
            {"InsertErr", "Failed to add item to collection."},
            {"UpdateSuc", "Item value updated successfully."},
            {"UpdateErr", " Failed to update item value."},
            {"RemoveKeySuc", "Item with the given key has been successfully deleted."},
            {"RemoveKeyErr", "Failed to delete item with specified key."},
            {"ClearSuc", "The collection has been cleared successfully."},
            {"ClearErr", " Failed to clear the collection."},
            {"RemoveGreaterSuc", "All greater items have been successfully removed."},
            {"RemoveGreaterErr", "Failed to delete greater items."},
            {"RemoveGreaterKeySuc", "All items with greater keys were successfully deleted."},
            {"RemoveGreaterKeyErr", "Failed to delete items with greater keys."},
            {"ReplaceIfGreaterSuc", "Item replaced successfully."},
            {"ReplaceIfGreaterErr", "Failed to replace item."},
            {"GroupCountingByCoordinatesResult", "There are {0} space marines in the first coordinate quarter, {1} in the second one, {2} in the third one, {3} in the fourth one."},
            {"FilterByChapterResult", "Elements whose chapter value is equal to entered value:\n"},
            {"FilterStartsWithNameResult", "Elements whose starts with entered value:\n"},
            {"ScriptSuccess", "The script has been successfully executed."},
            {"ScriptRunTime", "An error occurred while executing the script."},
            {"ScriptFile", "Failed to read file."},
            {"DataBaseError", "A database access error has occurred or connection has closed."},
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
