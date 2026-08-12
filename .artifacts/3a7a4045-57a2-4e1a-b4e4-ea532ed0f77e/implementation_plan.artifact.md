# Add Filter BottomSheet, CSV Export Screen, and Integrate Actions

Implement an `ActivityFilterBottomSheet` for multi-selection filtering, a `CsvReportScreen` for exporting data to CSV, and integrate these into the `MonitorScreen` via a `DropdownMenu` in the top bar.

## Proposed Changes

### UI Components

#### [NEW] [ActivityFilterBottomSheet.kt](file:///C:/Users/hdela/AndroidStudioProjects/EasyTimeTracking/app/src/main/java/com/budoxr/ett/ui/components/ActivityFilterBottomSheet.kt)
- Create based on `ActivitySelectionBottomSheet.kt`.
- Use `Checkbox` for multi-selection.
- Add "Apply" and "Clear" buttons.
- Update title to "Filter Activities".

#### [NEW] [CsvReportScreen.kt](file:///C:/Users/hdela/AndroidStudioProjects/EasyTimeTracking/app/src/main/java/com/budoxr/ett/ui/CsvReportScreen.kt)
- Create based on `ManageBackupScreen.kt`.
- Change header to "Generate Report".
- Set action button to "Export to .CSV".

### ViewModel / Presentation

#### [MODIFY] [MonitorViewModel.kt](file:///C:/Users/hdela/AndroidStudioProjects/EasyTimeTracking/app/src/main/java/com/budoxr/ett/presentation/presenters/MonitorViewModel.kt)
- Update `BottomSheetHandle` to include `showActivityFilter` and `showDateRangePicker`.
- Update `MonitorFormState` to store `selectedFilterActivities: Set<Long>` and `selectedDateRange: Pair<String, String>?`.
- Add methods: `onApplyActivityFilter`, `onClearActivityFilter`, `onDateRangeSelected`, `onClearFilters`.

#### [MODIFY] [ManageBackupViewModel.kt](file:///C:/Users/hdela/AndroidStudioProjects/EasyTimeTracking/app/src/main/java/com/budoxr/ett/presentation/presenters/ManageBackupViewModel.kt)
- Add `TypeOperation.ExportToCsv`.
- Implement `startExportCsv()` using `databaseBackupManager.exportToCsv()`.

### Screen UI

#### [MODIFY] [MonitorScreen.kt](file:///C:/Users/hdela/AndroidStudioProjects/EasyTimeTracking/app/src/main/java/com/budoxr/ett/ui/MonitorScreen.kt)
- Add state for `DropdownMenu` visibility.
- Update `GlobalTopBar` to trigger the `DropdownMenu`.
- Add menu items: "Activities", "Date Range", "Export to .CSV", "Remove filters".
- Integrate `ActivityFilterBottomSheet` and `DateRangePickerBottomSheet`.

## Verification Plan

### Manual Verification
1. **Filter Activities**: Open "Activities" filter, select multiple activities, apply, and verify the list is filtered.
2. **Date Range**: Open "Date Range" filter, select a range, apply, and verify historical data is filtered.
3. **Export CSV**: Select "Export to .CSV", navigate to `CsvReportScreen`, click "Export to .CSV", and verify the file is created in Downloads.
4. **Remove Filters**: Click "Remove filters" and verify all filters are cleared.
