# Xpenxe --- Production System Design

**Document type:** Product Requirements, UX/UI Specification, and
Technical Architecture\
**Product:** Xpenxe\
**Platform:** Android (Kotlin + Jetpack Compose)\
**Backend:** Node.js + Express + TypeScript\
**Database:** MySQL\
**Specification status:** Initial engineering baseline\
**Version:** 1.0

------------------------------------------------------------------------

## 1. Product overview

### 1.1 Product vision

Xpenxe is a polished personal finance application that helps people
record, understand, and manage their everyday spending. It should feel
calm, trustworthy, fast, and carefully designed. Its visual identity is
based on pure black, crisp white, and a distinctive purple accent.

Xpenxe is not merely a transaction form with charts. It is a complete
personal expense-management product with:

-   Secure account registration and sign-in.
-   Google sign-in.
-   Income and expense tracking.
-   Categories and transaction management.
-   Budgets and spending progress.
-   Financial dashboards and useful analytics.
-   Search, filtering, and transaction history.
-   Recurring transaction support.
-   Savings goals.
-   Offline transaction creation with automatic synchronisation.
-   User preferences, including appearance and accessibility settings.
-   Exportable financial data.

### 1.2 Product principles

1.  **Clarity over decoration:** Financial information must be easy to
    understand.
2.  **Fast capture:** Recording a transaction should take as few
    deliberate steps as practical.
3.  **Offline resilience:** A user can create and manage local
    transactions without an internet connection. Server-dependent
    operations remain online-only.
4.  **Privacy and security:** Financial information is private and
    protected in transit, at rest, and at the API boundary.
5.  **Predictable interaction:** Navigation, Back behaviour, forms,
    loading, errors, and destructive actions must be consistent.
6.  **Accessible by default:** Respect system font scaling, contrast,
    touch targets, TalkBack, and reduced-motion preferences where
    supported.
7.  **Performance is a feature:** Avoid unnecessary work,
    recompositions, database reads, network requests, and animation
    complexity.
8.  **Maintainable architecture:** UI, domain logic, persistence, and
    networking must have clear boundaries.
9.  **Measured motion:** Animations and haptics must reinforce
    meaningful interactions, not distract from financial tasks.

### 1.3 Initial release scope

The first production-style release should include:

-   Email/password authentication.
-   Google sign-in.
-   Dashboard.
-   Transaction creation, viewing, editing, deletion, search, and
    filters.
-   Categories.
-   Monthly budgets.
-   Insights and charts.
-   Recurring transaction definitions.
-   Savings goals.
-   Profile and settings.
-   Theme preferences.
-   Offline transaction persistence and synchronisation.
-   Account/session management.

Features such as bank-account aggregation, open banking, investment
tracking, bill payments, AI financial advice, multi-user household
accounts, and automatic bank feeds are **not** part of the initial
release. They may be considered after the core product is stable.

------------------------------------------------------------------------

## 2. Product identity and visual direction

### 2.1 Brand

-   **Name:** Xpenxe
-   **Product category:** Personal finance / expense management
-   **Brand personality:** Premium, minimal, confident, clear, modern.
-   **Primary accent:** Purple.
-   **Default currency:** Ghanaian cedi (`GHS`).
-   **Initial locale:** English.
-   **Initial region:** Ghana, while avoiding assumptions that prevent
    future internationalisation.

The name is a working product name. Before public release, perform
trademark, Play Store, domain, and social-handle checks.

### 2.2 Colour philosophy

The dark theme must be genuinely black, not a charcoal or grey canvas.
The light theme must be genuinely white. Use borders, spacing,
typography, and controlled tonal layers to establish hierarchy rather
than turning the entire dark interface into grey.

Suggested initial brand tokens (subject to visual review):

  Token              Light       Dark
  ------------------ ----------- -----------
  Background         `#FFFFFF`   `#000000`
  Surface            `#FFFFFF`   `#000000`
  Elevated surface   `#FFFFFF`   `#101010`
  Primary text       `#101010`   `#FFFFFF`
  Secondary text     `#55515D`   `#BDB8C7`
  Outline            `#E7E3ED`   `#292333`
  Primary accent     `#7138D8`   `#A879FF`
  Accent pressed     `#5B27BD`   `#C09BFF`
  Accent container   `#F0E9FF`   `#24143D`
  On accent          `#FFFFFF`   `#10051D`
  Success            `#167A4A`   `#54D69A`
  Warning            `#986000`   `#FFC45C`
  Error              `#B3261E`   `#FF7770`

These are semantic starting values, not permission to hardcode colours
in screens. All colours must be referenced through the design system.

For dark mode, preserve a black primary canvas. A slightly lifted
surface such as `#101010` may be used sparingly for a sheet or card when
separation is needed. Do not make the entire dark theme grey.

### 2.3 Theme modes

Provide three appearance modes:

-   `SYSTEM` --- default; follows Android's light/dark system setting
    while using Xpenxe's custom palette.
-   `LIGHT` --- always use Xpenxe's white theme.
-   `DARK` --- always use Xpenxe's black theme.

Provide an optional **Dynamic colours** setting:

-   Default: off.
-   Available only on Android 12 / API 31 and above.
-   When enabled, use Android dynamic colour schemes where supported.
-   When disabled, use Xpenxe's brand palette.
-   Persist the setting.
-   Explain in settings that dynamic colours may change the app's accent
    and surfaces to match the device wallpaper.
-   Do not silently enable dynamic colours or override a user's explicit
    preference.

If Dynamic colours is enabled, retain semantic transaction colours and
ensure charts remain legible. The app must still work correctly on older
Android versions.

### 2.4 Design tokens

No arbitrary styling values should be scattered through screen files.
Define central tokens and use them consistently.

Recommended design-system files:

``` text
core/designsystem/
├── theme/
│   ├── XpenxeTheme.kt
│   ├── Color.kt
│   ├── Type.kt
│   ├── Shape.kt
│   └── ThemeMode.kt
├── tokens/
│   ├── Spacing.kt
│   ├── Dimensions.kt
│   ├── TypographyTokens.kt
│   ├── IconSizes.kt
│   ├── ElevationTokens.kt
│   ├── MotionTokens.kt
│   └── HapticTokens.kt
└── components/
    ├── buttons/
    ├── cards/
    ├── inputs/
    ├── sheets/
    ├── dialogs/
    ├── charts/
    ├── feedback/
    └── loading/
```

Define tokens for:

-   Spacing scale.
-   Screen horizontal and vertical insets.
-   Component heights and minimum touch targets.
-   Icon sizes.
-   Corner radii.
-   Border widths.
-   Typography scale and weights.
-   Chart dimensions and label styles.
-   Motion durations and easing.
-   Haptic interaction categories.
-   Content maximum widths and adaptive breakpoints.

Use a small, intentional spacing scale (for example 4, 8, 12, 16, 20,
24, 32, 40, 48 dp) exposed as named tokens. The values are centralised
and can be tuned globally.

Do not create a token for every conceivable value. Tokens should
represent recurring design decisions. Use Material 3 typography and
colour abstractions where appropriate, extended by Xpenxe-specific
semantic tokens.

### 2.5 Typography

-   Use a high-quality, legible sans-serif typeface.
-   Prefer the Android system font initially unless a licensed bundled
    font is explicitly approved.
-   Define Material 3 typography roles in one location.
-   Use tabular numerals for currency values where practical.
-   Keep large balance figures visually prominent without overwhelming
    the screen.
-   Respect `fontScale`; do not disable user font scaling.
-   Avoid fixed-height text containers that clip enlarged text.
-   Format GHS values consistently, for example `GH₵ 1,250.00` or
    `GHS 1,250.00`. Select one display convention and use it throughout
    the app. The currency code in APIs remains `GHS`.

### 2.6 Iconography

-   Use one coherent icon family, preferably Material Symbols or an
    approved consistent vector set.
-   Use filled/outlined states only where they communicate selection.
-   Icons must have accessible descriptions when meaningful.
-   Decorative icons should be excluded from accessibility semantics.
-   Avoid emoji as functional icons.
-   Do not mix unrelated icon families or arbitrary stroke weights.
-   Centralise icon size tokens and reusable icon wrappers.

------------------------------------------------------------------------

## 3. UX and navigation specification

### 3.1 Navigation principles

Use a single, well-defined navigation architecture. The app should use
Navigation 3 with typed route keys if that is the chosen project
baseline and the dependency is stable for the selected Compose BOM. Do
not mix Navigation 3 with a second navigation framework.

Navigation responsibilities:

-   Root navigation owns authentication versus the signed-in
    application.
-   Primary destinations own the main application areas.
-   Secondary screens are pushed above the primary destination.
-   Forms and detail screens must not accidentally show primary bottom
    navigation.
-   Back navigation must return to the correct previous destination.
-   Deep links must resolve through the same navigation model.
-   Navigation state should survive configuration changes and process
    recreation where supported.

### 3.2 Custom bottom navigation requirement

The user will provide the existing customised bottom-navigation source
code and assets separately.

**Mandatory instruction for the implementation agent:**

1.  Do not invent or substitute a generic bottom navigation.
2.  Do not start implementing the bottom navigation before the user
    supplies the reference source and assets.
3.  Inspect the supplied source code, drawables, icon variants,
    dimensions, animation behaviour, selected/unselected states, and
    interaction patterns.
4.  Document what is reusable and what must be adapted to Xpenxe's
    navigation architecture.
5.  Recreate or adapt the custom navigation in Xpenxe while preserving
    its visual identity and behaviour.
6.  Keep navigation state and route selection separate from the visual
    component.
7.  Do not copy unrelated CampusFlow business logic into Xpenxe.
8.  If any required asset or code is missing, ask for it rather than
    guessing.

Primary navigation should be displayed only on top-level destinations.
It must not appear on:

-   Login, signup, password recovery, or OAuth callback flows.
-   Transaction create/edit screens.
-   Transaction detail screens.
-   Category management screens.
-   Budget detail/edit screens.
-   Savings goal detail/edit screens.
-   Settings subpages.
-   Any other secondary workflow unless explicitly approved in UX
    review.

### 3.3 Screen inventory

#### Authentication

1.  Welcome / entry screen.
2.  Sign in.
3.  Create account.
4.  Forgot password.
5.  Reset password deep-link destination.
6.  Google sign-in loading/error states.
7.  Terms and privacy links.

Authentication screens use focused layouts, no primary bottom
navigation, clear validation, password visibility control, and
accessible error messaging.

#### Primary application

1.  **Home / Dashboard**
    -   Greeting and date context.
    -   Current-month income, expense, and net balance summary.
    -   Budget progress overview.
    -   Recent transactions.
    -   Spending trend preview.
    -   Quick actions.
    -   Empty state for a new user.
    -   Pull-to-refresh only where remote refresh is meaningful.
2.  **Transactions**
    -   Transaction list grouped by date.
    -   Search.
    -   Filters for date range, type, category, and amount.
    -   Sort options.
    -   Pagination or incremental loading for remote history.
    -   Local pending-sync indicator.
    -   Empty, offline, loading, and error states.
3.  **Insights**
    -   Income versus expense.
    -   Spending over time.
    -   Category breakdown.
    -   Budget performance.
    -   Month-to-month comparison.
    -   Useful explanatory labels; avoid charts without context.
    -   Date-range selection.
4.  **Budgets**
    -   Monthly budget overview.
    -   Category budgets.
    -   Spent, remaining, and percentage used.
    -   Budget creation and editing.
    -   Budget warning states.
    -   Budget detail screen.
5.  **Profile / Settings entry**
    -   Account summary.
    -   Preferences.
    -   Appearance.
    -   Currency display preference (GHS initially).
    -   Notifications.
    -   Data export.
    -   Privacy and security.
    -   Help and about.
    -   Sign out and account deletion.

The exact primary destinations and bottom-navigation arrangement must be
aligned with the supplied custom navigation design.

#### Secondary workflows

-   Transaction detail.
-   Add transaction.
-   Edit transaction.
-   Category picker/manager.
-   Budget creation/edit/detail.
-   Savings goal list/create/detail/edit.
-   Recurring transaction list/create/edit.
-   Date-range picker.
-   Filter sheet.
-   Export flow.
-   Appearance settings.
-   Notification settings.
-   Account and security settings.
-   Delete account confirmation.

### 3.4 Top app bars

Use top app bars intentionally:

-   Top-level dashboard: small or medium app bar depending on visual
    hierarchy.
-   Transaction history: title, search action, filter action.
-   Detail screens: back navigation, title, contextual actions.
-   Forms: back/cancel, clear title, Save action where appropriate.
-   Settings pages: back navigation and page title.
-   Avoid redundant app bars on screens whose design already has a clear
    custom header.
-   Do not place multiple competing navigation controls in the same
    header.

Use Material 3 top app bar components where appropriate, with Xpenxe
styling.

### 3.5 Bottom sheets and dialogs

Use modal bottom sheets for short contextual choices, not for every
screen.

Good bottom-sheet use cases:

-   Category selection.
-   Date-range selection.
-   Transaction filters.
-   Sort selection.
-   Quick transaction options.
-   Budget-period selection.
-   Chart data-point details.
-   Short confirmation or action menus where appropriate.

Use full screens for complex forms or workflows. Use confirmation
dialogs for destructive actions such as permanent deletion. Every modal
must support dismissal, system Back, and appropriate accessibility
focus.

### 3.6 Responsive/adaptive layouts

Support phones, foldables, tablets, portrait, and landscape.

-   Use Window Size Classes or the current recommended adaptive Compose
    APIs.
-   Compact widths: single-column layouts and bottom navigation.
-   Medium widths: wider content with constrained maximum width;
    consider a navigation rail if appropriate and consistent with the
    supplied navigation design.
-   Expanded widths: use adaptive two-pane layouts for dashboards,
    transaction list/detail, or settings where beneficial.
-   Do not stretch phone layouts across very wide screens.
-   Respect display cutouts, edge-to-edge insets, IME insets, and
    gesture navigation.
-   Forms must remain usable when the keyboard is open.
-   Avoid fixed screen heights and hardcoded device-specific offsets.

------------------------------------------------------------------------

## 4. Functional requirements

### 4.1 Accounts and authentication

Support:

-   Email/password signup.
-   Email/password login.
-   Google sign-in.
-   Email verification.
-   Forgot password.
-   Password reset.
-   Access token refresh.
-   Sign out.
-   Revoke/clear local session.
-   Account deletion request.
-   Session-expiry handling.

The Android app must not implement authentication business logic itself.
The Express backend is authoritative for user identity and sessions.

Google sign-in:

-   Use Android Credential Manager with the Google identity provider.
-   Obtain a Google ID token for the backend's configured OAuth client.
-   Send the ID token to the backend over HTTPS.
-   Backend verifies signature, issuer, audience, expiry, and
    nonce/state as applicable.
-   Backend creates or links the application account according to
    explicit account-linking policy.
-   Backend issues Xpenxe application tokens.
-   Never treat a client-provided email address as proof of identity.
-   Never embed a Google client secret in the Android app.

### 4.2 Transactions

A transaction has:

-   ID (UUID).
-   Type: `INCOME` or `EXPENSE`.
-   Amount in minor units (pesewas).
-   Currency code (`GHS` initially).
-   Title/description.
-   Category ID.
-   Transaction date/time.
-   Optional note.
-   Optional merchant/payee.
-   Optional payment method.
-   Optional recurring-series ID.
-   Created/updated timestamps.
-   Sync status locally.
-   Version or revision metadata for conflict handling.

Required capabilities:

-   Create.
-   Read/list.
-   Update.
-   Delete.
-   Search.
-   Filter.
-   Sort.
-   View transaction detail.
-   Create while offline.
-   Update/delete local or cached transactions while offline, with
    changes queued for sync.
-   Retry failed synchronisation.
-   Show pending, synced, or failed status without overwhelming the UI.

Validation:

-   Amount must be greater than zero.
-   Amount precision must not exceed the currency's supported fraction
    digits.
-   Category must be valid for the user and transaction type.
-   Date must be valid.
-   Title must have a reasonable maximum length.
-   Notes must have a reasonable maximum length.
-   Prevent accidental duplicate submission with UI loading state and
    server idempotency.

### 4.3 Categories

-   Seed sensible default income and expense categories.
-   Users may create, rename, recolour, archive, and reorder custom
    categories.
-   A category cannot be permanently deleted while referenced by
    transactions unless a reassignment strategy is applied.
-   Archived categories remain available when displaying historical
    transactions.
-   Category selection should use a reusable bottom sheet or full-screen
    picker depending on the number of categories.

### 4.4 Budgets

-   Monthly overall budget.
-   Category-specific budgets.
-   Budget start/end period.
-   Spent amount calculated from eligible expense transactions.
-   Remaining amount.
-   Percentage used.
-   Warning thresholds configurable centrally (initially 75%, 90%, and
    100%).
-   Handle overspending without negative progress-bar glitches.
-   Do not double-count pending local transactions.
-   Clearly distinguish locally calculated totals from server-confirmed
    summaries when relevant.

### 4.5 Insights and charts

Initial chart set:

-   Income versus expenses over time.
-   Expense trend by day/week/month.
-   Spending by category.
-   Budget usage.
-   Month-to-month comparison.
-   Top spending categories.

Requirements:

-   Chart values must be derived from validated data.
-   Provide accessible textual summaries and labels.
-   Use a consistent chart palette.
-   Avoid relying on colour alone to distinguish series.
-   Support empty and insufficient-data states.
-   Show exact values on selection/tap.
-   Keep chart rendering lightweight.
-   Use a chart library only after evaluating maintenance,
    accessibility, Compose compatibility, and performance. Wrap the
    chosen library behind reusable Xpenxe chart components so screens do
    not depend directly on library APIs.
-   Do not animate every data point on every recomposition.

### 4.6 Recurring transactions

-   Create a recurring rule with frequency: daily, weekly, monthly,
    yearly, or custom supported intervals.
-   Start date and optional end date.
-   Next scheduled occurrence.
-   Pause/resume/cancel rule.
-   Generate occurrences idempotently on the backend for signed-in
    online accounts.
-   Clearly label generated transactions.
-   Do not generate duplicate occurrences during retries.
-   Offline recurring rule creation is out of the first offline scope
    unless explicitly implemented and tested. The initial release may
    require internet for recurring-rule management.

### 4.7 Savings goals

-   Goal name.
-   Target amount.
-   Current saved amount.
-   Target date (optional).
-   Progress.
-   Contribution history.
-   Pause/archive/delete.
-   Offline goal management is not required in the first release unless
    later prioritised.

### 4.8 Search, filters, and exports

-   Search transactions by title, merchant, and note.
-   Filters: date range, type, category, minimum/maximum amount, and
    sync status.
-   Sort by date, amount, or title.
-   Export CSV initially; PDF may be added after CSV is stable.
-   Export generation must be server-authorised or safely generated from
    local data, with clear disclosure of which dataset is exported.
-   Share/export intents must not leak data through logs or analytics.

### 4.9 Notifications

Optional notification features:

-   Budget threshold reminders.
-   Recurring transaction reminders.
-   Weekly/monthly spending summaries.
-   Sync issue notifications only when actionable.
-   Notification permission must be requested contextually, not
    immediately on first launch.
-   Users must control notification preferences.

------------------------------------------------------------------------

## 5. Android application architecture

### 5.1 Technology baseline

Use stable versions compatible with the project's selected Android
Gradle Plugin and Kotlin version. Do not blindly upgrade to alpha/beta
dependencies.

  -----------------------------------------------------------------------
  Concern                             Preferred technology
  ----------------------------------- -----------------------------------
  Language                            Kotlin

  UI                                  Jetpack Compose

  Design                              Material 3

  Architecture                        MVVM + UDF

  Navigation                          Navigation 3 with typed routes, if
                                      stable for the chosen dependency
                                      baseline

  State                               StateFlow

  Async                               Kotlin Coroutines and Flow

  Dependency injection                Hilt

  Local database                      Room

  Preferences                         DataStore

  HTTP                                Retrofit + OkHttp

  Serialization                       Kotlin Serialization

  Image loading                       Coil, only where images are needed

  Background work                     WorkManager

  Secure token storage                Android Keystore-backed encryption;
                                      use a maintained secure-storage
                                      approach

  Google sign-in                      Credential Manager

  Testing                             JUnit, kotlinx-coroutines-test,
                                      Turbine, MockK or an approved
                                      alternative, Compose UI tests

  Build                               Gradle Kotlin DSL, version catalog
  -----------------------------------------------------------------------

The agent must inspect the existing Android toolchain and choose
mutually compatible stable versions. Maintain a version catalog. Avoid
duplicate dependencies and unnecessary libraries.

### 5.2 Recommended project structure

Use a modular structure only when it provides clear ownership and build
benefits. Do not create dozens of empty modules. A professional
feature-first package structure inside a single app module is acceptable
for the first implementation; extract Gradle modules when boundaries and
build performance justify it.

Suggested structure:

``` text
com.xpenxe/
├── XpenxeApplication.kt
├── MainActivity.kt
├── app/
│   ├── XpenxeApp.kt
│   ├── AppState.kt
│   └── navigation/
│       ├── AppNavHost.kt
│       ├── AppRoute.kt
│       ├── AuthGraph.kt
│       └── MainGraph.kt
├── core/
│   ├── common/
│   │   ├── result/
│   │   ├── dispatcher/
│   │   ├── time/
│   │   └── validation/
│   ├── designsystem/
│   │   ├── theme/
│   │   ├── tokens/
│   │   └── components/
│   ├── database/
│   │   ├── XpenxeDatabase.kt
│   │   ├── dao/
│   │   ├── entity/
│   │   └── migration/
│   ├── network/
│   │   ├── api/
│   │   ├── dto/
│   │   ├── interceptor/
│   │   └── error/
│   ├── datastore/
│   ├── security/
│   ├── sync/
│   ├── analytics/
│   └── testing/
├── data/
│   ├── repository/
│   ├── mapper/
│   └── source/
│       ├── local/
│       └── remote/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── feature/
│   ├── auth/
│   │   ├── navigation/
│   │   ├── data/
│   │   ├── domain/
│   │   └── presentation/
│   ├── dashboard/
│   ├── transactions/
│   ├── categories/
│   ├── budgets/
│   ├── insights/
│   ├── recurring/
│   ├── goals/
│   ├── profile/
│   └── settings/
└── ui/
    └── navigation/
        └── supplied custom bottom navigation integration
```

The final package layout may be simplified or adjusted after inspecting
the project. Keep feature ownership clear. Avoid creating a package for
every tiny composable.

### 5.3 Layer responsibilities

**Presentation** - Compose screens and reusable feature components. -
ViewModels. - Immutable UI state. - UI events and one-off effects. - No
direct Retrofit, DAO, or database access.

**Domain** - Business models. - Repository interfaces. - Use cases for
meaningful business operations. - Pure validation and calculation
logic. - No Android framework or Retrofit dependencies.

**Data** - Repository implementations. - Local and remote data
sources. - DTO/entity/domain mapping. - Sync orchestration. - Error
translation.

**Core** - Shared infrastructure such as Room, Retrofit, design system,
DataStore, security, and dispatchers.

### 5.4 ViewModel and UI state conventions

Use immutable state exposed as `StateFlow`.

Example:

``` kotlin
data class TransactionsUiState(
    val isLoading: Boolean = false,
    val transactions: List<TransactionUiModel> = emptyList(),
    val query: String = "",
    val selectedFilter: TransactionFilter = TransactionFilter.All,
    val isRefreshing: Boolean = false,
    val error: UiMessage? = null,
    val isEmpty: Boolean = false
)
```

Recommended pattern:

-   `ViewModel` owns screen state and business event handling.
-   `StateFlow` exposes current state.
-   UI collects with lifecycle-aware collection.
-   User actions are explicit events or ViewModel methods.
-   One-off navigation and snackbar effects use a well-defined event
    mechanism; do not put transient events into persistent UI state
    without consumption semantics.
-   Avoid mutable state objects exposed to the UI.
-   Do not store `Context`, `Activity`, or Composable references in a
    ViewModel.
-   Do not use a ViewModel as a dumping ground for unrelated screens.
-   Keep state derivation predictable and testable.

### 5.5 Repository and use-case conventions

Repository interfaces belong in domain; implementations belong in data.

Examples:

-   `ObserveTransactionsUseCase`
-   `CreateTransactionUseCase`
-   `UpdateTransactionUseCase`
-   `DeleteTransactionUseCase`
-   `ObserveDashboardSummaryUseCase`
-   `ObserveBudgetProgressUseCase`
-   `SyncPendingChangesUseCase`

Do not create a use case for every trivial one-line repository call
without a reason. Use cases are valuable where they express business
operations, validation, orchestration, or reuse.

### 5.6 Dependency injection

Use Hilt consistently.

-   Application class annotated for Hilt.
-   Activity entry point configured.
-   Provide database, DAOs, Retrofit, API services, repositories,
    dispatchers, DataStore, and sync dependencies through modules.
-   Use constructor injection by default.
-   Avoid service locators and manual singleton objects.
-   Ensure test replacements can be injected.

------------------------------------------------------------------------

## 6. Local persistence and offline-first design

### 6.1 Source of truth

Room is the source of truth for transaction data displayed by the UI.

Data flow:

``` text
Compose Screen
    ↓ user event
ViewModel
    ↓
Use Case
    ↓
Repository
    ├── Room DAO → observable local data → UI
    └── Sync coordinator → Retrofit API → backend
```

The UI observes Room-backed `Flow` streams. A successful API response is
mapped and written to Room. The UI then updates from Room.

Do not make screens wait for a network response before displaying
already cached data.

### 6.2 Offline scope

When offline, users can:

-   View previously synchronised transactions.
-   View locally cached categories and budgets.
-   Create transactions.
-   Edit transactions available locally.
-   Delete transactions available locally.
-   See which changes are pending synchronisation.
-   Continue using locally available summaries.

Internet is required for:

-   Signup and login.
-   Google OAuth.
-   Password reset and email verification.
-   Remote account changes.
-   Initial data download.
-   Server-backed recurring-rule management.
-   Remote export jobs.
-   Any operation requiring authoritative server validation or remote
    data not cached locally.

If an operation requires internet, disable or guard the action with a
clear explanation. Do not pretend it succeeded.

### 6.3 Outbox pattern

Use a durable local outbox for offline mutations.

Suggested local tables:

-   `transactions`
-   `categories`
-   `budgets`
-   `sync_operations`
-   `sync_metadata`

A `sync_operations` record should contain:

-   `operation_id` (UUID).
-   `entity_type`.
-   `entity_id`.
-   `operation_type` (`CREATE`, `UPDATE`, `DELETE`).
-   Serialised payload or a typed payload representation.
-   `created_at`.
-   `attempt_count`.
-   `last_attempt_at`.
-   `last_error_code`.
-   `status` (`PENDING`, `IN_PROGRESS`, `FAILED`, `COMPLETED`).

Avoid storing secrets or access tokens in outbox payloads.

### 6.4 Synchronisation workflow

1.  User creates or changes a transaction.
2.  Repository commits the transaction and corresponding outbox
    operation in one Room database transaction.
3.  UI observes the Room change immediately.
4.  Sync coordinator schedules WorkManager work when network constraints
    are satisfied.
5.  Worker reads pending outbox operations in deterministic order.
6.  Worker submits a batch to the backend with idempotency identifiers.
7.  Backend validates ownership, deduplicates operations, and commits
    accepted changes.
8.  Worker applies server acknowledgements and canonical server records
    to Room.
9.  Completed outbox operations are marked complete or removed according
    to retention policy.
10. Worker fetches changes since the last successful cursor and applies
    them to Room.
11. UI updates automatically from Room.

### 6.5 Conflict policy

Implement explicit version-aware conflict handling.

-   Every server transaction has a monotonically increasing `version` or
    equivalent revision.
-   Client mutations include the base version where applicable.
-   Server returns a conflict response when the base version is stale.
-   For the initial release, do not silently overwrite a newer server
    edit.
-   Preserve the local pending change and surface a recoverable conflict
    state.
-   Provide a simple resolution policy: review server version, keep
    server version, or reapply the user's change as a new update.
-   Deletion must use a server tombstone or versioned delete record so a
    stale client does not resurrect deleted data.
-   Document how multiple offline edits to the same entity are coalesced
    or ordered.

### 6.6 Retry and idempotency

-   Use exponential backoff with jitter for retryable network errors.
-   Respect WorkManager constraints and retry policy.
-   Do not retry permanent validation or authorization errors
    indefinitely.
-   Use UUID idempotency keys for each mutation.
-   Backend must guarantee duplicate submissions do not create duplicate
    transactions.
-   Never rely solely on a disabled button for duplicate prevention.

### 6.7 Authentication and offline data

-   Cache only data belonging to the authenticated account.
-   On sign-out, apply a documented local-data policy.
-   Default recommendation: remove account-specific cached data and
    pending operations after confirming the user understands
    unsynchronised changes may be lost. Alternatively, require a
    successful sync or explicit discard confirmation before sign-out.
-   Never expose one account's local records to another account on a
    shared device.
-   Encrypt sensitive local data where threat assessment requires it;
    protect session tokens with Android Keystore-backed storage.
-   Do not log transaction descriptions, amounts, tokens, or personal
    financial data.

------------------------------------------------------------------------

## 7. Money and financial calculations

### 7.1 Representation

Never use `Float` or `Double` for money.

-   Currency: `GHS`.
-   Currency fraction digits: 2.
-   Store amounts as integer minor units (`BIGINT` on MySQL, `Long` on
    Kotlin).
-   Example: GH₵ 125.50 is stored as `12550` pesewas.
-   Format values using locale-aware currency formatting.
-   Perform percentage calculations with decimal-safe arithmetic.
-   Define rounding rules explicitly.
-   Avoid converting monetary amounts through binary floating-point
    values.

### 7.2 Sign convention

Store transaction type separately from amount:

-   `INCOME` with positive `amount_minor`.
-   `EXPENSE` with positive `amount_minor`.

Do not store expenses as negative amounts in the canonical transaction
record. Derive signed display values in the presentation layer.

### 7.3 Date and time

-   Store timestamps in UTC.
-   Store transaction's intended calendar date separately as an ISO date
    (`YYYY-MM-DD`) where required for financial reporting.
-   Use the user's configured time zone for date grouping and month
    boundaries.
-   Do not assume the device's current time zone is always the account's
    reporting time zone.
-   Define month boundaries consistently on client and server.

------------------------------------------------------------------------

## 8. Backend architecture

### 8.1 Technology baseline

-   Node.js LTS.
-   TypeScript strict mode.
-   Express.
-   MySQL 8 or a supported managed MySQL service.
-   A migration tool (Prisma Migrate, Knex migrations, or another
    selected migration system).
-   Zod or an equivalent schema validator.
-   OpenAPI 3.1.
-   Structured logging.
-   Centralised error middleware.
-   Automated tests.
-   Environment-based configuration.
-   Docker for local development if useful.

Choose one ORM/query approach and use it consistently. Do not mix raw
SQL, multiple ORMs, and ad hoc query builders without a compelling
reason.

### 8.2 Backend project structure

``` text
xpenxe-api/
├── src/
│   ├── app.ts
│   ├── server.ts
│   ├── config/
│   ├── common/
│   │   ├── errors/
│   │   ├── middleware/
│   │   ├── validation/
│   │   ├── logging/
│   │   └── types/
│   ├── modules/
│   │   ├── auth/
│   │   ├── users/
│   │   ├── transactions/
│   │   ├── categories/
│   │   ├── budgets/
│   │   ├── insights/
│   │   ├── recurring/
│   │   ├── goals/
│   │   ├── sync/
│   │   └── exports/
│   └── infrastructure/
│       ├── database/
│       ├── email/
│       ├── google/
│       └── jobs/
├── migrations/
├── tests/
├── openapi/
├── scripts/
├── Dockerfile
├── docker-compose.yml
├── package.json
└── tsconfig.json
```

Each module should separate routes/controllers, schemas,
service/business logic, repository/data access, and tests where useful.

### 8.3 API conventions

-   Base path: `/api/v1`.
-   HTTPS only outside local development.
-   JSON request and response bodies.
-   ISO 8601 timestamps in UTC.
-   Dates as `YYYY-MM-DD`.
-   UUID identifiers.
-   Pagination uses cursor-based pagination for large transaction lists.
-   Consistent error envelope.
-   Validate all input at the API boundary.
-   Enforce user ownership in every data query.
-   Never trust a `userId` supplied by the client for ownership.
-   Use request IDs for tracing.
-   Use idempotency keys for mutation endpoints where retries are
    possible.
-   Publish and maintain OpenAPI specification as the source of truth
    for API contracts.

### 8.4 Standard success and error envelopes

Success response example:

``` json
{
  "data": {},
  "meta": {
    "requestId": "uuid",
    "timestamp": "2026-10-30T12:00:00Z"
  }
}
```

Paginated response:

``` json
{
  "data": [],
  "meta": {
    "nextCursor": "opaque-cursor-or-null",
    "hasMore": false,
    "requestId": "uuid"
  }
}
```

Error response:

``` json
{
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "The request contains invalid fields.",
    "details": [
      {
        "field": "amountMinor",
        "reason": "Must be greater than zero."
      }
    ],
    "requestId": "uuid"
  }
}
```

Do not expose stack traces, SQL details, provider tokens, or internal
secrets in API errors.

------------------------------------------------------------------------

## 9. API contract

The following is the initial frontend/backend contract. Backend
implementation and Android integration must follow the published OpenAPI
document. Any change to a request, response, enum, or error code must
update OpenAPI and the Android DTOs together.

### 9.1 Authentication

  ------------------------------------------------------------------------------------------
  Method            Endpoint                             Purpose           Auth
  ----------------- ------------------------------------ ----------------- -----------------
  POST              `/api/v1/auth/register`              Create account    Public

  POST              `/api/v1/auth/login`                 Email/password    Public
                                                         login             

  POST              `/api/v1/auth/google`                Exchange Google   Public
                                                         ID token for      
                                                         Xpenxe session    

  POST              `/api/v1/auth/refresh`               Refresh session   Refresh token

  POST              `/api/v1/auth/logout`                Revoke current    Access
                                                         session           

  POST              `/api/v1/auth/forgot-password`       Request password  Public
                                                         reset             

  POST              `/api/v1/auth/reset-password`        Reset password    Public
                                                         using token       

  POST              `/api/v1/auth/verify-email`          Verify email      Public

  POST              `/api/v1/auth/resend-verification`   Resend            Public,
                                                         verification      rate-limited
                                                         email             

  GET               `/api/v1/auth/me`                    Current           Access
                                                         authenticated     
                                                         user              
  ------------------------------------------------------------------------------------------

Register request:

``` json
{
  "name": "Example User",
  "email": "user@example.com",
  "password": "strong-password"
}
```

Login request:

``` json
{
  "email": "user@example.com",
  "password": "strong-password"
}
```

Google exchange request:

``` json
{
  "idToken": "google-id-token",
  "nonce": "request-nonce"
}
```

Token response (illustrative):

``` json
{
  "data": {
    "accessToken": "short-lived-access-token",
    "accessTokenExpiresAt": "2026-10-30T12:15:00Z",
    "refreshToken": "rotating-refresh-token",
    "refreshTokenExpiresAt": "2026-11-29T12:00:00Z",
    "user": {
      "id": "user-uuid",
      "name": "Example User",
      "email": "user@example.com",
      "emailVerified": true
    }
  }
}
```

Security requirements:

-   Store passwords using Argon2id or a well-reviewed equivalent.
-   Rate-limit login, signup, reset, and verification endpoints.
-   Use refresh-token rotation and server-side revocation.
-   Store refresh tokens hashed in the backend database.
-   Access tokens should be short-lived.
-   Avoid storing long-lived tokens in plain preferences.
-   Handle refresh races with a single-flight token refresh mechanism in
    the Android networking layer.
-   On refresh failure, clear the session and navigate to authentication
    safely.
-   Never log tokens or passwords.

### 9.2 User profile and preferences

  Method   Endpoint                         Purpose
  -------- -------------------------------- ---------------------------
  GET      `/api/v1/users/me`               Get profile
  PATCH    `/api/v1/users/me`               Update profile
  GET      `/api/v1/users/me/preferences`   Get server preferences
  PATCH    `/api/v1/users/me/preferences`   Update server preferences
  DELETE   `/api/v1/users/me`               Request account deletion

Appearance preferences may be stored locally because they are
device-specific. Account-level preferences such as reporting time zone
can be synchronised with the backend.

### 9.3 Transactions

  ----------------------------------------------------------------------------------------
  Method                  Endpoint                                 Purpose
  ----------------------- ---------------------------------------- -----------------------
  GET                     `/api/v1/transactions`                   List transactions with
                                                                   filters and cursor

  POST                    `/api/v1/transactions`                   Create transaction

  GET                     `/api/v1/transactions/{transactionId}`   Get detail

  PATCH                   `/api/v1/transactions/{transactionId}`   Update transaction

  DELETE                  `/api/v1/transactions/{transactionId}`   Delete transaction
  ----------------------------------------------------------------------------------------

List query parameters:

-   `cursor`
-   `limit`
-   `type`
-   `categoryId`
-   `fromDate`
-   `toDate`
-   `minAmountMinor`
-   `maxAmountMinor`
-   `search`
-   `sort` (`DATE_DESC`, `DATE_ASC`, `AMOUNT_DESC`, `AMOUNT_ASC`)

Create request:

``` json
{
  "id": "client-generated-uuid",
  "type": "EXPENSE",
  "amountMinor": 12550,
  "currency": "GHS",
  "title": "Groceries",
  "categoryId": "category-uuid",
  "transactionDate": "2026-10-30",
  "note": "Weekly shopping",
  "merchant": "Local supermarket",
  "paymentMethod": "CASH",
  "clientMutationId": "mutation-uuid"
}
```

Response:

``` json
{
  "data": {
    "id": "client-generated-uuid",
    "type": "EXPENSE",
    "amountMinor": 12550,
    "currency": "GHS",
    "title": "Groceries",
    "categoryId": "category-uuid",
    "transactionDate": "2026-10-30",
    "note": "Weekly shopping",
    "merchant": "Local supermarket",
    "paymentMethod": "CASH",
    "version": 1,
    "createdAt": "2026-10-30T12:00:00Z",
    "updatedAt": "2026-10-30T12:00:00Z"
  }
}
```

Update requests should include a version or `If-Match` precondition. Use
a consistent optimistic-concurrency strategy.

### 9.4 Categories

  -----------------------------------------------------------------------------------
  Method                  Endpoint                            Purpose
  ----------------------- ----------------------------------- -----------------------
  GET                     `/api/v1/categories`                List default and user
                                                              categories

  POST                    `/api/v1/categories`                Create custom category

  PATCH                   `/api/v1/categories/{categoryId}`   Update category

  DELETE                  `/api/v1/categories/{categoryId}`   Archive or delete when
                                                              safe
  -----------------------------------------------------------------------------------

Category fields:

-   `id`
-   `name`
-   `type` (`INCOME`, `EXPENSE`)
-   `iconKey`
-   `colorKey`
-   `isDefault`
-   `isArchived`
-   `sortOrder`

Use semantic icon and colour keys rather than accepting arbitrary
executable or resource values from the server.

### 9.5 Budgets

  Method   Endpoint                                Purpose
  -------- --------------------------------------- ---------------
  GET      `/api/v1/budgets`                       List budgets
  POST     `/api/v1/budgets`                       Create budget
  GET      `/api/v1/budgets/{budgetId}`            Budget detail
  PATCH    `/api/v1/budgets/{budgetId}`            Update budget
  DELETE   `/api/v1/budgets/{budgetId}`            Delete budget
  GET      `/api/v1/budgets/{budgetId}/progress`   Get progress

Budget request:

``` json
{
  "name": "October budget",
  "amountMinor": 250000,
  "currency": "GHS",
  "period": "MONTHLY",
  "startDate": "2026-10-01",
  "endDate": "2026-10-31",
  "categoryId": null,
  "warningThresholds": [75, 90, 100]
}
```

### 9.6 Dashboard and insights

  Method   Endpoint                        Purpose
  -------- ------------------------------- ----------------------------
  GET      `/api/v1/dashboard/summary`     Dashboard summary
  GET      `/api/v1/insights/cashflow`     Income/expense time series
  GET      `/api/v1/insights/categories`   Category breakdown
  GET      `/api/v1/insights/trends`       Spending trends
  GET      `/api/v1/insights/comparison`   Period comparison

All insight endpoints accept a clearly defined date range and time zone.
Responses must state the range and currency. For offline mode, calculate
cached summaries locally from Room data and label them as based on
locally available records when appropriate.

### 9.7 Recurring transactions

  Method   Endpoint                                Purpose
  -------- --------------------------------------- ----------------------
  GET      `/api/v1/recurring-transactions`        List recurring rules
  POST     `/api/v1/recurring-transactions`        Create rule
  GET      `/api/v1/recurring-transactions/{id}`   Get rule
  PATCH    `/api/v1/recurring-transactions/{id}`   Update/pause/resume
  DELETE   `/api/v1/recurring-transactions/{id}`   Cancel rule

### 9.8 Savings goals

  Method   Endpoint                                 Purpose
  -------- ---------------------------------------- ----------------------
  GET      `/api/v1/goals`                          List goals
  POST     `/api/v1/goals`                          Create goal
  GET      `/api/v1/goals/{goalId}`                 Goal detail
  PATCH    `/api/v1/goals/{goalId}`                 Update goal
  DELETE   `/api/v1/goals/{goalId}`                 Archive/delete goal
  POST     `/api/v1/goals/{goalId}/contributions`   Add contribution
  GET      `/api/v1/goals/{goalId}/contributions`   Contribution history

### 9.9 Synchronisation

  Method   Endpoint                Purpose
  -------- ----------------------- --------------------------------
  POST     `/api/v1/sync/push`     Submit queued client mutations
  GET      `/api/v1/sync/pull`     Fetch changes after a cursor
  GET      `/api/v1/sync/status`   Optional diagnostic status

Push request:

``` json
{
  "deviceId": "installation-uuid",
  "operations": [
    {
      "operationId": "mutation-uuid",
      "entityType": "TRANSACTION",
      "operation": "CREATE",
      "entityId": "transaction-uuid",
      "baseVersion": null,
      "payload": {
        "type": "EXPENSE",
        "amountMinor": 12550,
        "currency": "GHS",
        "title": "Groceries",
        "categoryId": "category-uuid",
        "transactionDate": "2026-10-30"
      }
    }
  ]
}
```

Push response:

``` json
{
  "data": {
    "accepted": [
      {
        "operationId": "mutation-uuid",
        "entityId": "transaction-uuid",
        "version": 1
      }
    ],
    "conflicts": [],
    "rejected": []
  }
}
```

Pull query:

-   `cursor`: opaque cursor returned by the previous successful pull.
-   `limit`: bounded page size.

Pull response:

``` json
{
  "data": {
    "changes": [],
    "nextCursor": "opaque-cursor",
    "hasMore": false
  }
}
```

Sync requirements:

-   Push operations are idempotent.
-   Each operation is authorised against the authenticated user.
-   Backend validates entity ownership and payload schemas.
-   Pull cursor is scoped to the authenticated account.
-   Deletions are represented in a way that clients can apply reliably.
-   Server returns per-operation accepted/conflict/rejected outcomes.
-   Client does not discard failed operations until it has a defined
    resolution.
-   Batch size is bounded.
-   Sync is safe to retry after process death or network interruption.

### 9.10 Exports

  ---------------------------------------------------------------------------------------
  Method                  Endpoint                                Purpose
  ----------------------- --------------------------------------- -----------------------
  POST                    `/api/v1/exports`                       Request export

  GET                     `/api/v1/exports/{exportId}`            Check export status

  GET                     `/api/v1/exports/{exportId}/download`   Download completed
                                                                  export
  ---------------------------------------------------------------------------------------

Initially support CSV. Protect download links, use short-lived
authorisation, and avoid public permanent URLs.

### 9.11 Health and readiness

  Method   Endpoint          Purpose
  -------- ----------------- -------------------------------
  GET      `/health/live`    Process liveness
  GET      `/health/ready`   Database/dependency readiness

Do not expose sensitive infrastructure details in public health
responses.

------------------------------------------------------------------------

## 10. MySQL data model

Use InnoDB, foreign keys, appropriate indexes, UTC timestamps, and
migrations. All user-owned records must include an ownership
relationship that is enforced by backend queries.

The following is a logical schema baseline. Exact column types and ORM
syntax are selected in the backend implementation.

### 10.1 Tables

#### `users`

-   `id CHAR(36) PRIMARY KEY`
-   `name VARCHAR(120) NOT NULL`
-   `email VARCHAR(255) NOT NULL UNIQUE`
-   `password_hash VARCHAR(255) NULL`
-   `email_verified_at DATETIME NULL`
-   `timezone VARCHAR(64) NOT NULL DEFAULT 'Africa/Accra'`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`
-   `deleted_at DATETIME NULL`

`password_hash` is nullable for accounts created through a provider-only
flow, subject to account-linking policy.

#### `auth_identities`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `provider VARCHAR(32) NOT NULL`
-   `provider_subject VARCHAR(255) NOT NULL`
-   `created_at DATETIME NOT NULL`
-   Unique key on (`provider`, `provider_subject`).
-   Foreign key to users.

Do not store Google ID tokens as reusable application credentials.

#### `refresh_tokens`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `token_hash VARCHAR(255) NOT NULL`
-   `device_id VARCHAR(100) NULL`
-   `expires_at DATETIME NOT NULL`
-   `revoked_at DATETIME NULL`
-   `created_at DATETIME NOT NULL`
-   Index by `user_id`, `expires_at`.

#### `password_reset_tokens`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `token_hash VARCHAR(255) NOT NULL`
-   `expires_at DATETIME NOT NULL`
-   `used_at DATETIME NULL`
-   `created_at DATETIME NOT NULL`

#### `email_verification_tokens`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `token_hash VARCHAR(255) NOT NULL`
-   `expires_at DATETIME NOT NULL`
-   `used_at DATETIME NULL`
-   `created_at DATETIME NOT NULL`

#### `categories`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NULL` (null for global defaults)
-   `name VARCHAR(80) NOT NULL`
-   `type ENUM('INCOME','EXPENSE') NOT NULL`
-   `icon_key VARCHAR(64) NOT NULL`
-   `color_key VARCHAR(64) NOT NULL`
-   `is_default BOOLEAN NOT NULL DEFAULT FALSE`
-   `is_archived BOOLEAN NOT NULL DEFAULT FALSE`
-   `sort_order INT NOT NULL DEFAULT 0`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`
-   Index by (`user_id`, `type`, `is_archived`).

#### `transactions`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `category_id CHAR(36) NOT NULL`
-   `type ENUM('INCOME','EXPENSE') NOT NULL`
-   `amount_minor BIGINT NOT NULL`
-   `currency CHAR(3) NOT NULL DEFAULT 'GHS'`
-   `title VARCHAR(120) NOT NULL`
-   `note VARCHAR(1000) NULL`
-   `merchant VARCHAR(160) NULL`
-   `payment_method VARCHAR(32) NULL`
-   `transaction_date DATE NOT NULL`
-   `recurring_rule_id CHAR(36) NULL`
-   `version BIGINT NOT NULL DEFAULT 1`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`
-   `deleted_at DATETIME NULL`
-   Indexes for (`user_id`, `transaction_date`), (`user_id`,
    `category_id`, `transaction_date`), (`user_id`, `type`,
    `transaction_date`).
-   Check constraint `amount_minor > 0`.

#### `budgets`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `category_id CHAR(36) NULL`
-   `name VARCHAR(100) NOT NULL`
-   `amount_minor BIGINT NOT NULL`
-   `currency CHAR(3) NOT NULL DEFAULT 'GHS'`
-   `period VARCHAR(20) NOT NULL`
-   `start_date DATE NOT NULL`
-   `end_date DATE NOT NULL`
-   `warning_thresholds JSON NOT NULL`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`
-   `deleted_at DATETIME NULL`
-   Index by (`user_id`, `start_date`, `end_date`).

#### `recurring_rules`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `category_id CHAR(36) NOT NULL`
-   `type ENUM('INCOME','EXPENSE') NOT NULL`
-   `amount_minor BIGINT NOT NULL`
-   `currency CHAR(3) NOT NULL DEFAULT 'GHS'`
-   `title VARCHAR(120) NOT NULL`
-   `frequency VARCHAR(24) NOT NULL`
-   `interval_value INT NOT NULL DEFAULT 1`
-   `start_date DATE NOT NULL`
-   `end_date DATE NULL`
-   `next_occurrence_date DATE NOT NULL`
-   `status VARCHAR(16) NOT NULL`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`

#### `recurring_occurrences`

-   `id CHAR(36) PRIMARY KEY`
-   `recurring_rule_id CHAR(36) NOT NULL`
-   `transaction_id CHAR(36) NOT NULL`
-   `occurrence_date DATE NOT NULL`
-   Unique key (`recurring_rule_id`, `occurrence_date`).

#### `savings_goals`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `name VARCHAR(100) NOT NULL`
-   `target_amount_minor BIGINT NOT NULL`
-   `current_amount_minor BIGINT NOT NULL DEFAULT 0`
-   `currency CHAR(3) NOT NULL DEFAULT 'GHS'`
-   `target_date DATE NULL`
-   `status VARCHAR(16) NOT NULL`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`

#### `goal_contributions`

-   `id CHAR(36) PRIMARY KEY`
-   `goal_id CHAR(36) NOT NULL`
-   `user_id CHAR(36) NOT NULL`
-   `amount_minor BIGINT NOT NULL`
-   `contribution_date DATE NOT NULL`
-   `note VARCHAR(500) NULL`
-   `created_at DATETIME NOT NULL`

#### `sync_mutations`

-   `id CHAR(36) PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `device_id VARCHAR(100) NULL`
-   `operation_id CHAR(36) NOT NULL`
-   `entity_type VARCHAR(32) NOT NULL`
-   `entity_id CHAR(36) NOT NULL`
-   `result JSON NOT NULL`
-   `created_at DATETIME NOT NULL`
-   Unique key (`user_id`, `operation_id`).

#### `sync_changes`

-   `sequence_id BIGINT AUTO_INCREMENT PRIMARY KEY`
-   `user_id CHAR(36) NOT NULL`
-   `entity_type VARCHAR(32) NOT NULL`
-   `entity_id CHAR(36) NOT NULL`
-   `operation VARCHAR(16) NOT NULL`
-   `entity_version BIGINT NOT NULL`
-   `payload JSON NULL`
-   `created_at DATETIME NOT NULL`
-   Index by (`user_id`, `sequence_id`).

#### `user_preferences`

-   `user_id CHAR(36) PRIMARY KEY`
-   `reporting_currency CHAR(3) NOT NULL DEFAULT 'GHS'`
-   `timezone VARCHAR(64) NOT NULL DEFAULT 'Africa/Accra'`
-   `notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE`
-   `created_at DATETIME NOT NULL`
-   `updated_at DATETIME NOT NULL`

### 10.2 Database rules

-   Use migrations for every schema change.
-   Never modify production schema manually.
-   Enforce foreign keys and ownership checks.
-   Use transactions for multi-table mutations.
-   Index real query patterns, not every column.
-   Avoid storing computed dashboard totals as the only source of truth.
-   Use soft deletion/tombstones where offline sync requires it.
-   Add retention and cleanup jobs for expired tokens and old sync
    records.
-   Use UTC for timestamps.
-   Review JSON columns carefully; use relational columns for fields
    that need filtering, joining, or constraints.

------------------------------------------------------------------------

## 11. Reusable UI component catalogue

Build shared components when they have consistent behaviour and visual
rules. Do not over-generalise unrelated screens into one giant
component.

Potential shared components:

### App-wide

-   `XpenxeScaffold`
-   `XpenxeTopAppBar`
-   Supplied/adapted custom bottom navigation
-   `XpenxeScreenContent`
-   `XpenxeButton`
-   `XpenxeIconButton`
-   `XpenxeTextField`
-   `XpenxeAmountField`
-   `XpenxeCard`
-   `XpenxeBottomSheet`
-   `XpenxeConfirmDialog`
-   `XpenxeSnackbarHost`
-   `XpenxeEmptyState`
-   `XpenxeErrorState`
-   `XpenxeLoadingState`
-   `XpenxeLoadingSkeleton`
-   `XpenxeOfflineBanner`
-   `XpenxeSyncStatusIndicator`

### Finance components

-   `MoneyText`
-   `BalanceSummaryCard`
-   `IncomeExpenseSummary`
-   `TransactionListItem`
-   `TransactionDateHeader`
-   `TransactionTypeChip`
-   `CategoryIcon`
-   `CategoryPickerSheet`
-   `BudgetProgressCard`
-   `BudgetProgressIndicator`
-   `ChartContainer`
-   `ChartLegend`
-   `ChartTooltip`
-   `SavingsGoalCard`
-   `RecurringRuleCard`

Component rules:

-   Components receive data and callbacks; they do not own business
    logic.
-   Use stable, explicit parameters.
-   Keep reusable components focused.
-   Avoid exposing every internal styling parameter unless a real use
    case requires it.
-   Use tokens rather than hardcoded dimensions.
-   Provide previews for important reusable components in light and dark
    themes.
-   Test accessibility semantics and click targets.
-   Do not duplicate a component merely because two screens use slightly
    different data.

------------------------------------------------------------------------

## 12. Motion, transitions, gestures, and haptics

### 12.1 Motion principles

Motion must be purposeful, restrained, and fast. It should communicate
state changes and spatial relationships.

Define central motion tokens:

-   Short interaction duration.
-   Standard component transition duration.
-   Emphasis/large transition duration.
-   Standard easing.
-   Emphasised easing.
-   Spring stiffness and damping presets.

Use Compose animation APIs such as:

-   `AnimatedVisibility`
-   `animateContentSize`
-   `animate*AsState`
-   `AnimatedContent`
-   `Crossfade`
-   Navigation transition APIs
-   `Animatable` only when custom control is justified

Do not animate large portions of the screen on every data refresh. Avoid
expensive blur, excessive shadows, and continuous infinite animations.

### 12.2 Required motion moments

-   Screen transitions: subtle directional/fade transitions appropriate
    to navigation.
-   Bottom sheets: standard Material motion with consistent scrim.
-   FAB expansion: short scale/fade or slide transitions.
-   Transaction creation: subtle confirmation feedback.
-   Budget progress: animate on meaningful value changes, not every
    recomposition.
-   Charts: animate initial appearance and selected data only.
-   Loading: restrained skeleton/shimmer or progress indicator; respect
    reduced motion.
-   Theme changes: avoid flashing; apply theme state predictably.

### 12.3 Gestures

Use gestures only when discoverable and accessible.

Potential gestures:

-   Swipe transaction row to reveal actions, with confirmation for
    deletion.
-   Pull-to-refresh for screens with remote refresh.
-   Tap chart points to reveal details.
-   Horizontal paging for month navigation where appropriate.
-   Long-press only for secondary actions with visible alternatives.

Every gesture action must also be accessible through a visible control
or menu.

### 12.4 Haptics

Use haptic feedback sparingly and consistently.

Appropriate moments:

-   Successful transaction save.
-   Confirmed destructive action, if appropriate.
-   Selection changes in a meaningful picker.
-   Budget threshold reached.
-   FAB expansion only if it improves perceived affordance and does not
    become repetitive.

Avoid haptics on every keystroke, every scroll, every chart animation,
or every recomposition.

Use Android's supported haptic APIs through a small abstraction. Respect
system haptic settings and gracefully do nothing on unsupported devices.
Do not make business logic depend on haptics.

------------------------------------------------------------------------

## 13. Security, privacy, and compliance baseline

This application handles sensitive financial information.

### Android

-   HTTPS only.
-   No secrets in source code or BuildConfig checked into version
    control.
-   Store tokens using a Keystore-backed secure storage approach.
-   Never log access/refresh tokens, passwords, financial notes, or full
    transaction payloads.
-   Validate deep links and OAuth redirect parameters.
-   Use network security configuration that disallows cleartext in
    production.
-   Clear account-specific local data on sign-out according to the
    unsynced-data policy.
-   Protect sensitive screens from accidental exposure where justified;
    do not block legitimate screenshots without a clear product/security
    requirement.
-   Avoid leaking financial information through notifications on the
    lock screen by default.
-   Use least-privilege permissions.
-   Request notification permission contextually.
-   Do not request contacts, location, SMS, or unrelated permissions.

### Backend

-   Hash passwords with Argon2id or an approved password hashing
    algorithm.
-   Hash refresh/reset/verification tokens before storage.
-   Rotate refresh tokens.
-   Revoke sessions on logout and security events.
-   Validate JWT issuer, audience, expiry, and signature.
-   Apply rate limiting and abuse protection.
-   Validate all request payloads.
-   Enforce ownership at query/service level.
-   Use parameterised queries or safe ORM methods.
-   Configure CORS appropriately for any web clients; native Android is
    not protected by CORS.
-   Use secure headers.
-   Store secrets in environment/secret management.
-   Restrict database network access.
-   Encrypt backups and define restoration procedures.
-   Use audit events for security-sensitive operations without recording
    unnecessary financial detail.
-   Implement account deletion and data retention policies.
-   Protect exports with authorisation and expiring downloads.

### Privacy

-   Publish a privacy policy before public release.
-   Explain what data is collected and why.
-   Provide account deletion.
-   Provide a way to export user data.
-   Avoid advertising SDKs and unnecessary analytics in the initial
    release.
-   If analytics are introduced, collect minimal event metadata and
    never include transaction amounts, descriptions, merchant names, or
    account identifiers in event properties.

------------------------------------------------------------------------

## 14. Error handling and user feedback

Define a shared error taxonomy:

-   `NETWORK_UNAVAILABLE`
-   `TIMEOUT`
-   `UNAUTHENTICATED`
-   `FORBIDDEN`
-   `VALIDATION`
-   `NOT_FOUND`
-   `CONFLICT`
-   `RATE_LIMITED`
-   `SERVER`
-   `UNKNOWN`

Map technical failures to useful user messages. Do not display raw
exception messages.

Every screen with asynchronous data must define:

-   Initial loading.
-   Content.
-   Empty state.
-   Recoverable error.
-   Offline state where relevant.
-   Refreshing state where relevant.

Forms must define:

-   Field-level validation.
-   Submission state.
-   Server validation errors.
-   Duplicate submission protection.
-   Keyboard and focus behaviour.
-   Success feedback.
-   Safe navigation after success.

Use snackbars for brief feedback, inline errors for field-specific
problems, and dialogs only when the user must make a decision.

------------------------------------------------------------------------

## 15. Performance and quality requirements

### Android performance

-   Keep main-thread work minimal.
-   Use Room/IO dispatchers for database and network work.
-   Use lifecycle-aware collection.
-   Use lazy lists for potentially long collections.
-   Provide stable keys for list items.
-   Avoid expensive work in Composable bodies.
-   Use `remember`, `derivedStateOf`, and stable models where they
    provide measurable benefit.
-   Avoid premature use of `@Stable` or `@Immutable`; annotations must
    be truthful.
-   Avoid unnecessary nested scrolling.
-   Avoid repeated formatter and date parser construction in hot paths.
-   Use pagination for large histories.
-   Use image loading only where needed.
-   Profile recomposition and frame timing rather than guessing.
-   Avoid large JSON payloads and redundant API calls.
-   Cache categories and other relatively stable reference data.
-   Use debouncing for search.
-   Ensure sync workers do not run duplicate concurrent sync operations
    for the same account/device.

### Backend performance

-   Add indexes for demonstrated query patterns.
-   Paginate transaction history.
-   Avoid N+1 queries.
-   Use database transactions for atomic changes.
-   Apply request size limits.
-   Bound sync batch size.
-   Cache only safe, user-scoped data where justified.
-   Monitor slow queries and error rates.
-   Avoid returning unneeded fields.

### Quality targets

-   No crashes in core user journeys.
-   No financial precision errors.
-   No cross-account data leakage.
-   No duplicate transactions caused by sync retries.
-   No lost locally committed transaction due to process death.
-   No primary bottom navigation on secondary screens.
-   No unhandled authentication expiry.
-   No inaccessible primary action.
-   No severe jank in routine scrolling and navigation on representative
    mid-range devices.

------------------------------------------------------------------------

## 16. Testing strategy

### Android unit tests

Test:

-   Money formatting and parsing.
-   Currency minor-unit conversion.
-   Date and month-boundary calculations.
-   Budget progress and threshold logic.
-   Transaction validation.
-   Repository behaviour.
-   ViewModel state transitions.
-   Authentication state transitions.
-   Sync queue ordering and retry behaviour.
-   Conflict resolution.
-   Theme preference persistence.
-   Use cases.

### Android integration tests

Test:

-   Room DAO queries.
-   Database migrations.
-   Repository local/remote coordination.
-   Token refresh and request authentication.
-   WorkManager sync execution.
-   Offline transaction creation and restart recovery.
-   Sync idempotency from the client perspective.

### Compose UI tests

Test:

-   Authentication forms.
-   Transaction creation and validation.
-   Transaction list filtering.
-   Bottom-sheet interactions.
-   Theme switching.
-   Bottom navigation visibility and selection.
-   Back navigation.
-   Loading/empty/error states.
-   Large font scale and accessibility semantics.
-   FAB and action menus.

### Backend tests

Test:

-   Auth flows.
-   Google token verification using controlled test doubles.
-   Password hashing.
-   Refresh-token rotation and revocation.
-   User ownership isolation.
-   Transaction CRUD.
-   Validation and idempotency.
-   Sync push/pull.
-   Conflict behaviour.
-   Budget calculations.
-   Insights aggregation.
-   Account deletion.
-   Rate limiting.

### End-to-end acceptance scenarios

1.  New user registers and verifies email.
2.  User signs in with Google.
3.  User creates an expense online.
4.  User creates an expense offline, closes the app, reopens it, and
    sees the expense.
5.  Connectivity returns and the expense synchronises exactly once.
6.  User edits a transaction on another device and the first device
    receives the update.
7.  A conflict is detected and resolved without silent data loss.
8.  User creates a monthly budget and sees correct progress.
9.  User changes theme to dark, restarts the app, and the preference
    remains.
10. User enables dynamic colours on a supported Android version and can
    disable them again.
11. User navigates into a secondary screen and the primary bottom
    navigation is absent.
12. User signs out and another account cannot see the previous account's
    cached data.

------------------------------------------------------------------------

## 17. Observability and operations

### Android

-   Crash reporting may be added after privacy review.
-   Record non-sensitive diagnostic events such as sync success/failure
    categories and app version.
-   Do not log financial content.
-   Provide a diagnostic screen or exportable redacted logs for
    troubleshooting only if needed.

### Backend

-   Structured JSON logs.
-   Request correlation IDs.
-   Metrics for latency, error rate, sync outcomes, and database health.
-   Alerting for elevated authentication failures, server errors, and
    sync failures.
-   Health/readiness endpoints.
-   Database backup and restore runbooks.
-   Migration and rollback procedures.
-   Staging and production environments.
-   No production data in development fixtures.

------------------------------------------------------------------------

## 18. Delivery batches and acceptance criteria

**The AI coding agent must work in batches. It must not attempt to
implement the whole application in one pass.**

At the end of each batch:

1.  Summarise files created/changed.
2.  Explain architectural decisions briefly.
3.  Run relevant tests and build tasks.
4.  Report exact build/test results.
5.  List known issues.
6.  Stop and wait for approval before starting the next batch.

Do not claim a build or test passed unless it was actually run and the
result was observed.

### Batch 0 --- Repository inspection and project foundation

**Goal:** Understand the workspace and establish safe conventions.

Tasks: - Inspect existing repository, Gradle setup, package name,
Kotlin/Compose versions, and build variants. - Inspect supplied custom
bottom-navigation source and assets if provided. - If the
bottom-navigation reference is not yet provided, record it as a blocked
integration task. Do not invent its appearance. - Confirm app ID/package
namespace with the user before changing it. - Establish version catalog
and stable compatible dependencies. - Configure Kotlin, Compose, Hilt,
Room, DataStore, Retrofit, and testing foundations. - Establish code
style, lint, and build conventions. - Create a minimal app shell and
verify a clean build.

Acceptance: - Project builds. - Dependencies are compatible. - No
unnecessary dependency duplication. - No fabricated bottom-navigation
implementation. - A concise architecture map exists.

### Batch 1 --- Design system and app shell

**Goal:** Build the visual foundation before feature screens.

Tasks: - Implement brand colour palettes. - Implement `XpenxeTheme`. -
Implement System/Light/Dark mode. - Implement optional Dynamic colours
with default off and API 31+ guard. - Implement spacing, dimensions,
typography, shapes, icon, elevation, and motion tokens. - Establish
edge-to-edge behaviour and adaptive screen container. - Build reusable
buttons, cards, fields, loading, empty, error, snackbar, and sheet
primitives. - Add previews for light/dark and accessibility font
scaling. - Integrate the supplied bottom navigation only after
inspecting its code/assets.

Acceptance: - All design tokens are centralised. - No arbitrary screen
styling values. - Theme preference persists. - Light and dark themes are
visually coherent. - Dynamic colours are optional and safely gated. -
Bottom navigation is not shown on secondary routes.

### Batch 2 --- Navigation and authentication UI

**Goal:** Establish app flow and authentication experience.

Tasks: - Typed route model. - Root auth/session routing. - Sign-in,
signup, forgot-password, reset-password, and verification screens. -
Form validation and UI states. - Google sign-in UI integration
boundary. - Secure token storage abstraction. - Navigation/back
handling. - Deep-link handling for reset and verification.

Acceptance: - Navigation flows work with fake repositories. - No primary
bottom navigation on auth screens. - Forms handle loading, errors,
keyboard, and validation. - No hardcoded secrets. - OAuth does not trust
client-provided identity data.

### Batch 3 --- Backend foundation and API contract

**Goal:** Create the separate Express API project and authoritative
contract.

Tasks: - TypeScript Express project. - Environment configuration and
validation. - MySQL connection and migration setup. - OpenAPI 3.1
specification. - Error envelope, validation, request IDs, logging, rate
limiting. - Auth/user modules. - Database tables for users, identities,
sessions, reset and verification tokens. - Email provider abstraction. -
Google ID-token verification. - Integration tests.

Acceptance: - API starts in local/staging configuration. - Migrations
run cleanly. - OpenAPI validates. - Auth endpoints pass tests. - Secrets
are externalised. - Ownership and security rules are documented.

### Batch 4 --- Android networking, auth integration, and session lifecycle

**Goal:** Connect the Android app to the real API.

Tasks: - Retrofit services from the OpenAPI contract. - DTOs and
mappers. - Auth repository. - Login/signup/Google sign-in. - Token
refresh and rotation handling. - Single-flight refresh. - Logout and
session expiry. - Error mapping. - Secure storage. - Integration tests.

Acceptance: - Real authentication works against staging/local API. -
Expired access tokens refresh correctly. - Invalid refresh token safely
returns user to auth. - Tokens never appear in logs. - Duplicate refresh
requests are controlled.

### Batch 5 --- Local database and transaction foundation

**Goal:** Establish local-first transaction persistence.

Tasks: - Room database and migrations. - Transaction, category, and
outbox entities. - DAOs. - Domain models and repository interfaces. -
Repository implementations. - Default category seeding strategy. - Money
and date utilities. - Fake repository support for previews/tests.

Acceptance: - Transaction data is persisted locally. - Money uses
integer minor units. - DAO tests pass. - Database migrations are
tested. - UI can observe transaction data without network access.

### Batch 6 --- Transaction experience

**Goal:** Deliver complete transaction management.

Tasks: - Dashboard transaction preview. - Transaction history. - Add
transaction. - Edit transaction. - Transaction detail. - Delete with
confirmation. - Category picker. - Search, filter, sort. - Loading,
empty, error, and offline states. - Keyboard/focus and amount input
behaviour.

Acceptance: - Full local transaction CRUD works. - Invalid values are
rejected. - Amount formatting is correct. - Transaction changes survive
process restart. - Large lists scroll smoothly. - Secondary screens hide
primary navigation.

### Batch 7 --- Offline sync engine

**Goal:** Safely synchronise offline transaction changes.

Tasks: - WorkManager sync worker. - Network constraints. - Outbox
orchestration. - Push/pull API integration. - Idempotency. -
Retry/backoff. - Conflict handling. - Tombstones. - Account-scoped local
data. - Sync status UI.

Acceptance: - Offline create/edit/delete survives process death. -
Reconnection synchronises changes exactly once. - Duplicate requests do
not create duplicate records. - Conflicts are visible and recoverable. -
Failed operations are not silently discarded. - Account isolation is
verified.

### Batch 8 --- Dashboard, budgets, and categories

**Goal:** Make the application useful for ongoing financial management.

Tasks: - Dashboard summary. - Category management. - Monthly budgets. -
Category budgets. - Budget progress. - Warning thresholds. - Backend
calculations and endpoints. - Local cached summaries.

Acceptance: - Totals reconcile with transaction data. - Budgets handle
date boundaries and overspending. - Category archive behaviour preserves
history. - Offline cached information is labelled appropriately.

### Batch 9 --- Insights and charts

**Goal:** Provide meaningful financial analytics.

Tasks: - Cash-flow charts. - Category breakdown. - Spending trends. -
Period comparison. - Chart selection/tooltips. - Accessible chart
summaries. - Backend aggregation endpoints. - Local fallback
calculations where practical.

Acceptance: - Charts match tested calculation fixtures. - Empty and
insufficient-data states are clear. - Charts are readable in both
themes. - No misleading data or unlabelled axes. - Rendering remains
responsive.

### Batch 10 --- Recurring transactions and savings goals

**Goal:** Add longer-term planning features.

Tasks: - Recurring rules and occurrence generation. - Idempotent
scheduled backend jobs. - Recurring UI. - Savings goal CRUD. -
Contribution history. - Progress visualisations.

Acceptance: - Recurring occurrences are not duplicated. -
Paused/cancelled rules behave correctly. - Goal calculations are
accurate. - Scheduling is timezone-aware.

### Batch 11 --- Settings, notifications, and exports

**Goal:** Complete account controls and utility features.

Tasks: - Appearance settings. - Dynamic colour preference. -
Notification preferences and permissions. - CSV export. -
Privacy/security settings. - Account deletion flow. - About/help. - Data
export and retention handling.

Acceptance: - Preferences persist. - Notification permission is
contextual. - Exports are authorised and correct. - Account deletion
follows documented policy. - Sensitive information is not exposed in
notifications or logs.

### Batch 12 --- Polish, accessibility, performance, and release readiness

**Goal:** Production-quality verification.

Tasks: - Motion and haptic review. - Accessibility audit. - Responsive
layout audit. - Compose performance profiling. - API/database
performance review. - Security review. - Unit, integration, UI, and
end-to-end test completion. - Crash/error handling. - Release build
configuration. - CI pipeline. - Privacy policy and Play Store
preparation. - Backup/restore and operational runbooks.

Acceptance: - Core journeys pass. - No known critical security or
data-integrity defects. - Performance issues are measured and
addressed. - Release build is reproducible. - Documentation and
operational procedures are complete.

------------------------------------------------------------------------

## 19. Definition of done

A feature is not complete merely because its screen looks correct.

A feature is done only when:

-   UI follows the design system.
-   Screen state is immutable and lifecycle-aware.
-   Business logic is outside Composables.
-   Repository/data boundaries are respected.
-   Loading, success, empty, error, and offline states are handled.
-   Validation exists on both client and server where applicable.
-   Accessibility semantics are present.
-   Responsive behaviour is considered.
-   Unit/UI/integration tests appropriate to the feature pass.
-   No sensitive data is logged.
-   No unnecessary comments or duplicated components are introduced.
-   Build succeeds.
-   User-facing behaviour has been manually or automatically verified.

------------------------------------------------------------------------

## 20. Engineering constraints and non-goals

The implementation agent must not:

-   Build all batches at once.
-   Replace the supplied custom bottom navigation with a generic
    component.
-   Add random colours, padding, font sizes, icon sizes, or animation
    durations directly in screen files.
-   Use `Float` or `Double` for money.
-   Put API calls inside Composables.
-   Put business logic inside Composables.
-   Access Room directly from a ViewModel.
-   Store tokens in plain preferences.
-   Trust client-supplied user IDs.
-   Log financial or authentication secrets.
-   silently discard offline operations.
-   Use unbounded retry loops.
-   Add libraries without explaining their purpose.
-   Create giant files containing multiple unrelated screens.
-   Duplicate reusable components.
-   Add comments that merely restate the code.
-   Claim tests/builds passed without running them.
-   Use placeholder data in production paths without clearly separating
    fixtures from real repositories.
-   Change architecture or API contracts without documenting the reason
    and updating affected tests/specification.

The agent should ask before making a decision that changes product
scope, authentication/security semantics, data-loss behaviour, the
supplied navigation design, or a public API contract.

------------------------------------------------------------------------

## 21. Initial implementation order

The recommended order is:

1.  Inspect workspace and receive navigation reference.
2.  Approve project identity, package name, and toolchain.
3.  Build design system and app shell.
4.  Define and validate API contract.
5.  Build backend auth and database foundation.
6.  Integrate Android authentication.
7.  Build local transaction storage.
8.  Build transaction UI and CRUD.
9.  Build offline sync.
10. Add budgets, dashboard, insights, recurring, goals, settings.
11. Complete accessibility, performance, security, and release work.

Android UI work may use fake repositories while backend endpoints are
being built, but fake data must be clearly isolated and must not leak
into production configuration.

**End of system design.**
