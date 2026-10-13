# Movement work remaining

The product's historical behavior and exact Minecraft source references are documented beside each registered implementation under `src/main/java/me/wolfii/legacyparkourcompat/change/`. Read the [decompiler guide](../../buildSrc/README.md) for the canonical source tree and mapping provenance. Source comments identify compared files/members/ranges and remaining evidence limits; they are not a runtime-parity claim.

[Current backlog](backlog.json) contains **160 unfinished findings**: **109 open** and **51 partially implemented**, plus **4 source/player-applicability claims**. Each finding states its assertion, source identities, bounded review evidence, implementation links and concrete remaining work. **12 existing Changes** still lack a complete independently reviewed operation/source binding; candidate locators and missing bindings are explicit in the code and backlog.

Fully represented findings no longer duplicate the implementation in separate documents. There are no campaign archives, migration crosswalks or copied source summaries in this tree. Immutable source-review revisions remain recoverable through Git; a missing exact review artifact stays an explicit gate.

All listed source-pair inventories/dependency closure/independent coverage audits remain incomplete. Per-finding acceptance does not freeze a whole pair. Compilation and tick-level runtime parity remain separate; runtime parity is unverified. Use the [working procedures](../workflows/README.md) before separately authorized source, implementation or verification work.

Keep this index current: add an independently resolved pending operation once, close its source/review/applicability gates, and move implemented behavior/source references into its Change class. Partial operations remain until their exact coverage gaps are resolved.

[Scope and refuted claims](decisions.md) records exclusions and invalid witnesses needed to avoid repeating unsupported work.
