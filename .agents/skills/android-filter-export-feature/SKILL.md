---
name: android-filter-export-feature
description: >-
  Create a new BottomSheet component and a CSV Export Screen based on reference implementations,
  and update the parent UI/ViewModel layer to integrate the new actions.
---

### How to use this template:
Replace the placeholder bracketed variables `[VARIABLE_NAME]` with your specific code components before sending. Refer to `AGENTS.md` for architectural and state management guidelines.

#### Variable Reference:
* `[NEW_BOTTOMSHEET_NAME]` -> e.g., `ActivityFilterBottomSheet`
* `[BASED_BOTTOMSHEET_NAME]` -> e.g., `ActivitySelectionBottomSheet.kt`
* `[ALT_BOTTOMSHEET_NAME]` -> e.g., `DateRangePickerBottomSheet.kt`
* `[UI_FILE_SCREEN]` -> e.g., `MonitorScreen.kt`
* `[NEW_EXPORT_CSV_SCREEN_NAME]` -> e.g., `CsvReportScreen.kt`
* `[BASED_EXPORT_CSV_SCREEN_NAME]` -> e.g., `ManageBackupScreen.kt`
* `[ACTION_BUTTON_CLICK_NAME]` -> e.g., `onActionButtonClick`
* `[MENU_LOCATION]` -> e.g., `TopAppBar trailing content`
* `[CUSTOM_UI_CHANGES]` -> List of specific modifications for the new BottomSheet.
* `[CUSTOM_CSV_SCREEN_CHANGES]` -> List of specific modifications for the new CSV Export Screen.


# Add Filter BottomSheet, Export Screen, and Integrate Actions

## 1. BottomSheet Creation & UI Layer
Create or modify `[NEW_BOTTOMSHEET_NAME]` using `[BASED_BOTTOMSHEET_NAME]` as a structural baseline.

### UI Modifications for `[NEW_BOTTOMSHEET_NAME]`:
[CUSTOM_UI_CHANGES]
* Change title or labels as required by the design.
* Adjust item selectors (e.g., replace standard list items with Checkboxes/Radio buttons).
* Expose item selection states properly through the ViewModel or local UI state.

### Parent Screen Updates (`[UI_FILE_SCREEN]`):
Add a `DropdownMenu` positioned in `[MENU_LOCATION]` containing the following menu items:
* **Activities:** Displays `[NEW_BOTTOMSHEET_NAME]` upon click.
* **Date Range:** Displays `[ALT_BOTTOMSHEET_NAME]` upon click.
* **Export to .CSV:** Navigates/Displays `[NEW_EXPORT_CSV_SCREEN_NAME]` upon click.
* **Remove filters:** Clears all selected activities and entered date ranges upon click.


## 2. CSV Export Screen Creation
Create `[NEW_EXPORT_CSV_SCREEN_NAME]` using `[BASED_EXPORT_CSV_SCREEN_NAME]` as a structural baseline (configured for `typeOperation: ExportToJson` or CSV operation equivalent).

### UI & Logic Modifications for `[NEW_EXPORT_CSV_SCREEN_NAME]`:
[CUSTOM_CSV_SCREEN_CHANGES]
* Change the screen/header text to **"Generate Report"**.
* Set the action button label to **'Export to .CSV'**.
* Wire the action button to invoke `exportToCsv` in `DatabaseBackupManager`, using the `startExportJson` implementation pattern in `ManageBackupViewModel` as a reference.


## 3. ViewModel & Event Handling (`[UI_FILE_SCREEN]`)
* Integrate the open/close state of the `DropdownMenu`, as well as selection values for `[NEW_BOTTOMSHEET_NAME]` and `[ALT_BOTTOMSHEET_NAME]`, into the screen's existing UI State.
* Ensure the `[ACTION_BUTTON_CLICK_NAME]` event correctly toggles/triggers the display of the `DropdownMenu`.


## ⚠️ Important Constraints:
* Do **not** break, delete, or rewrite existing business logic or unrelated UI methods.
* Only add the new functionality and wire up necessary state connectors so the transition from the UI down to the presentation/data layers operates seamlessly.
