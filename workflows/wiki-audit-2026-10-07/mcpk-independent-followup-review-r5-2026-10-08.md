# Independent MCPK follow-up review — r5 (2026-10-08)

**Verdict: ACCEPT, bounded to the two r4 correction requests and preservation of prior accepted corrections.** This is not a full audit of the follow-up, a source-pair freeze, or authorization to implement mechanics.

## Reviewed object and scope

- Candidate: `fe37af704b2213ec58fcdd049af1fadfae016ee0:workflows/wiki-audit-2026-10-07/mcpk-independent-followup-2026-10-08-r5.md`
- Git blob: `b5d6bd29e4a2e919c0c67f888728d862dfd8bcd5`
- Raw UTF-8 SHA-256: `c126e2be6e79b73fe3ae60b5e1093e11a787a68bde2e9c4cd54bbf18e0a9948d`
- Owner tip reviewed: `58213301184a33c1a32aaf94da8160abd45b91b7` (`fix/mcpk-clean-rereview-dispositions`). The candidate is reachable from that tip.
- Review branch: `fix/mcpk-r5-independent-review`, based on the owner tip. Local `main` at `3a60fe735560e478bf0aa0d05f5e306c74800f6a` is an ancestor of the review branch.
- Scope was limited to the r4 re-review requests, the corresponding cited exact-version ready sources, the two preserved earlier corrections, and the r5 binding. No Minecraft Wiki, implementation, or other pair reports were consulted.

## Checks

1. **1.13.2 Y=256 jump consumer and producer edges — corrected.** r5 names `LivingEntity.mobTick()` and gives its method and water-depth branch ranges. The cited Feather source confirms `LivingEntity.tick()` calls `mobTick()`, `PlayerEntity.mobTick()` delegates to `super.mobTick()`, and the jump branch selects normal jump versus `jumpInLiquid(WATER)` from the recorded water-depth field. The cited `Entity` path refreshes water state from `baseTick()`, checks water, and stores the overlap scan result in that field. Hashes and ranges match the supplied ready files. The report correctly stops short of identifying the initiating Y=256 state change or proving a fix boundary. Feather-to-original-JAR equivalence remains explicitly unproven.
2. **Blip revision 3476 fall-damage statement — correctly unresolved.** r5 routes the 1.15+ cancellation and alleged 1.16 patch as page-reported, unverified claims; it does not infer trajectories or mechanism from powder-snow evidence. It keeps damage resolution out of scope, leaves any direct movement-path `fallDistance` producer unverified, and records that 1.16.0 source is absent. This is the requested disposition.
3. **Earlier corrections — preserved.** The `SoulsandBlock.java` capitalization and `Entity.makeStuckInBlock` source references remain present in r5. The powder-snow/player path is not used to support the Blip report.

## Remaining limits

The accepted verdict applies only to this bounded re-review. The report itself correctly leaves the Y=256 initiating state and exact fix release open, and leaves the Blip symptom/mechanism and 1.16 boundary open. It also retains its broader partial-coverage and source-provenance caveats; this review does not promote those observations to discovery completion or implementation findings.

No tests, builds, decompilation, runtime validation, servers, Docker, or push operations were run.
