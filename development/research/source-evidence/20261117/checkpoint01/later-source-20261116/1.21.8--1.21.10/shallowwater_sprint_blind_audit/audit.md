# Blind source audit: shallow-water sprint and flight

**Verdict: ACCEPT** for the bounded source claim in `finding.md`, bound to SHA-256 `F1B949F06E1029939C50E716429A3512D93E2C2C74058FFB6F4F62ED68CACE65`. The assigned finding directory contains only this one Markdown form; its hash matches the supplied revision.

## Source identity and integrity

- 1.21.8 and 1.21.10 manifests identify exact version IDs and `mojmap`. Their provenance hashes match the supplied values (`90FCB370…BB4CFA` and `9A1DB4C0…D7AD77C`). Focused `LocalPlayer.java`, `Player.java`, `Abilities.java`, and `Entity.java` bytes match the respective `.sources.sha256` ledgers.
- The 1.21.9 hidden `.decompile-provenance.json` hash matches `37D30494…D9B72C4F`. Its pinned release metadata URL names `1.21.9.json`; its client mappings input is the Mojang `client.txt`, and the focused `LocalPlayer.java`, `Player.java`, `Abilities.java`, and `Entity.java` hashes match its per-source ledger.
- The source input is therefore exact Mojmap for all three releases. `LocalPlayer.java` is byte-identical in the 1.21.9 and 1.21.10 outputs; the later endpoint is consistent with the intermediate.

## Paired trace

- In 1.21.8 `LocalPlayer.canStartSprinting()` (`LocalPlayer.java:1065–1075`) ends with `(!this.isInWater() || this.isUnderWater())`. In the same class, `shouldStopRunSprinting()` (`:837–844`) also stops sprinting for `isInWater() && !isUnderWater()`. Thus the shallow-water gate blocks both starting and retaining a run sprint, regardless of `Abilities.flying`.
- In 1.21.9 and 1.21.10 `LocalPlayer.isSprintingPossible(boolean)` (`:1055–1060`) applies `($$0 || !this.isInShallowWater())`. The run start and run stop callers pass `this.getAbilities().flying` (`canStartSprinting():1062–1069`; `shouldStopRunSprinting():841–845`). `Entity.isInShallowWater()` (`Entity.java:1449–1451` in 1.21.9; `:1454–1456` in 1.21.10) is `isInWater() && !isUnderWater()`. Consequently, with flight enabled, this specific shallow-water exclusion short-circuits, permitting both sprint start and continuation subject to the other gates.
- The reachable client path is supported by `LocalPlayer.aiStep()`: for `mayfly`, always-flying mode sets `Abilities.flying`, or jump input can toggle it when not swimming (`LocalPlayer.java:750–770` in 1.21.9/1.21.10; corresponding block in 1.21.8). These writes occur after the sprint checks in that method, so a newly toggled flight flag affects the next tick's sprint check. `hasEnoughFoodToSprint()` permits mayfly players, while `isMobilityRestricted()` remains a blindness check.
- The consequence is grounded in the movement consumer: `Player.getFlyingSpeed()` doubles configured flying speed while sprinting (`Player.java:2045–2050` in 1.21.8; `:1867–1872` in 1.21.9/1.21.10), and `Player.travel()` selects its flying branch using `getAbilities().flying` (`:1438–1460` and `:1295–1317`, respectively). This establishes a changed sprint eligibility/state path; it does not predict a specific trajectory.

## Boundary and limits

The delta is absent in exact 1.21.8 and present in exact 1.21.9, so 1.21.9 is the verified introduction boundary for this claim; 1.21.10 confirms persistence. The draft's statement that the pair remains partial, incomplete, and unaudited is appropriate. This verdict accepts only the flying run-sprint shallow-water exemption. It does not accept the separately widened swimming sprint path (`shouldStopSwimSprinting()` passes `true`), close fluid-state or all ability-writer dependencies, audit the complete pair, or establish runtime parity.
