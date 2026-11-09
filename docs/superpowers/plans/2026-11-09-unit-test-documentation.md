# Unit-Test Documentation Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Replace the functional-manual test documentation with an accurate project-level test plan and detailed JUnit 5 test-case catalogue derived from the existing test suite.

**Architecture:** `TEST_PLAN.md` remains a concise, project-level management document describing scope, strategy, environments, execution, metrics and acceptance criteria. `TEST_CASES.md` becomes the evidence-oriented catalogue: each existing JUnit test method receives one traceable case, grouped by test level and source package. Tests that require SQL Server or Selenium remain documented but are explicitly segregated from the unit-test count.

**Tech Stack:** Java 17, Maven Surefire 3.2.5, JUnit Jupiter 5.10.2, SQL Server contract tests, Selenium 4.20.0.

**Spec:** `C:\Users\ADMIN\IdeaProjects\SOF309_SD21307_TV00362_HoangAnhKhoa\docs\SPECIFICATIONS.md`

## Global Constraints

- Retain Vietnamese as the primary document language and preserve the project/student metadata already established in the repository.
- Do not claim results that were not produced by an evidenced test execution; use `N/A` as the initial result and status.
- Classify a test by its actual dependencies, not by its file name: isolated tests are unit tests; tests gated by `QLSV_DB_CONTRACT=true` are DB contract/integration tests; WebDriver tests are UI tests.
- Keep all counts reproducible from `src/test/java` and explain exclusions configured in `pom.xml`.
- Do not expose live passwords, connection strings or personal data beyond the existing project metadata.

## Review Focus

- Test methods with `@ParameterizedTest` or nested cases must be catalogued, not omitted by a search limited to `@Test`.
- Surefire exclusions must not be counted as default-suite results.
- SQL Server tests gated by `QLSV_DB_CONTRACT` must not be represented as isolated unit tests.
- The Selenium login test must be recorded outside the unit-test scope.
- Every test-case row must point to an existing class and method, with an assertion aligned to the source implementation.

---

### Task 1: Build the JUnit test inventory and classification map

**Files:**
- Read: `src/test/java/**/*.java`
- Read: `pom.xml`
- Produce: source-backed inventory used in `docs/TEST_CASES.md`

**Interfaces:**
- Consumes: JUnit annotations, test class names, test method names, Maven Surefire include/exclude configuration and dependency markers.
- Produces: test identifier, source file, target class/method, test level, prerequisites, input/fixture, assertion summary and normal execution command.

- [ ] **Step 1: Enumerate every JUnit test method and parameterized test source under `src/test/java`**

Run: `rg -n "@(Test|ParameterizedTest|RepeatedTest)" src/test/java -g "*.java"`

Expected: every executable test method has a source path and line reference.

- [ ] **Step 2: Categorize each test by source dependency**

Mark `QLSV_DB_CONTRACT`-gated tests as DB contract/integration, `WebDriver` tests as UI automation, and all remaining isolated tests as unit or servlet/filter contract tests.

- [ ] **Step 3: Reconcile the inventory with Maven Surefire**

Run: `mvn test -DskipTests`.

Expected: Maven resolves the project and the documented default-suite exclusions match `pom.xml`.

### Task 2: Rewrite the project-level test plan

**Files:**
- Modify: `docs/TEST_PLAN.md`

**Interfaces:**
- Consumes: Task 1 inventory, `pom.xml`, and `SPECIFICATIONS.md`.
- Produces: a test-plan reference used by each row in `TEST_CASES.md`.

- [ ] **Step 1: Replace the manual-functional scope with the actual automated-test scope**

Document the purpose, in-scope packages/layers, out-of-scope UI/UX assessment, and the distinct treatment of unit, DB contract/integration and Selenium tests.

- [ ] **Step 2: Document the test strategy and environment**

Specify JUnit 5, Maven Surefire, Java 17, optional SQL Server contract environment, `QLSV_DB_CONTRACT=true`, test-data isolation requirements, and the commands for default and DB-enabled execution.

- [ ] **Step 3: Define execution governance and measurable exit criteria**

Add entry/exit criteria, status definitions, defect handling, execution report fields, risks/dependencies, and count rules that prevent optional tests from being reported as default-suite unit tests.

- [ ] **Step 4: Validate the test-plan structure**

Run: `rg -n "Mục tiêu|Phạm vi|Chiến lược|Môi trường|Tiêu chí|Rủi ro|Báo cáo" docs/TEST_PLAN.md`

Expected: all mandatory management-plan sections are present and no table claims unverified Pass results.

### Task 3: Replace the manual functional cases with source-traceable JUnit cases

**Files:**
- Modify: `docs/TEST_CASES.md`

**Interfaces:**
- Consumes: Task 1 test inventory and `TEST_PLAN.md` execution/status conventions.
- Produces: one test case per executable JUnit method, with traceability to source code.

- [ ] **Step 1: Create a concise legend, count summary and test-level classification table**

Use deterministic identifiers by test level and package; list the source count, default-suite eligibility and execution prerequisites.

- [ ] **Step 2: Add detailed cases for isolated unit tests**

For every eligible test method, record the source class/method, subject under test, fixture/input, action, expected assertion, priority and initial `N/A` status.

- [ ] **Step 3: Add detailed cases for servlet/filter contracts and optional integration/UI tests**

Preserve the same traceability columns while explicitly stating mock/request fixtures, DB opt-in conditions or browser/Tomcat prerequisites as applicable.

- [ ] **Step 4: Verify source-to-document coverage**

Run: a script or search reconciliation that compares documented `Class#method` references with annotated tests in `src/test/java`.

Expected: every documented reference resolves to source and all test methods are represented once.

### Task 4: Execute and report evidence without altering application code

**Files:**
- Modify: `docs/TEST_PLAN.md`
- Modify: `docs/TEST_CASES.md`

**Interfaces:**
- Consumes: rewritten documents and Maven test report output.
- Produces: evidence-backed summary values and `N/A` statuses where external prerequisites prevented execution.

- [ ] **Step 1: Run the default Maven test suite**

Run: `mvn test`.

Expected: record the exact tests run, passed, failed, skipped and errors from Surefire output; do not invent a passing outcome.

- [ ] **Step 2: Reconcile documented counts and statuses with execution output**

Update only the execution summary and status fields supported by current test evidence; leave optional DB/UI cases `N/A` or `Blocked` when prerequisites are unavailable.

- [ ] **Step 3: Inspect the final Markdown diff**

Run: `git diff -- docs/TEST_PLAN.md docs/TEST_CASES.md`.

Expected: changes only replace unsupported manual claims with source-backed, unit-test-oriented documentation.
