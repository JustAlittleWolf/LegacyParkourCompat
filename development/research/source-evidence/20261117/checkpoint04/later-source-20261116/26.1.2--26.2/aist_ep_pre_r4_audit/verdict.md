# Independent audit verdict — aist_ep_pre r4

**Audited report:** `build/later-source-20261116/26.1.2--26.2/aist_ep_pre_revision_r4/report-r4.md`  
**SHA-256:** `203C01B767A8CFC225066498C5FB2F916C928812DCADF8744DD8BCC1301263EB`

**Verdict: ACCEPT the corrected bounded source comparison.** The r4 report correctly withdraws the unsupported same-tick consequence from r3. It still makes no pair acceptance, onset, full-freeze, implementation, or runtime-parity claim.

## Audit basis

- The report bytes match the assigned SHA-256. Compared with r3, the only changed text is the movement-meaning sentence in item 4; the previous broader provenance/source audit is bound to r3 verdict `build/later-source-20261116/26.1.2--26.2/aist_ep_pre_r3_audit/verdict.md`, SHA-256 `45959f2b2283b614371d77b01b30f4d35e5c2c353c845ff3406adb99cc8c5087`.
- Per the unchanged-evidence reuse rule, I reused that verdict's ready/provenance/source-manifest and other cited Java-file checks. I rechecked the changed claim against both exact `Player.java` files. Their SHA-256 values remain the report-bound `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` (26.1.2) and `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` (26.2).
- In both sources, `Player.aiStep()` resets fall distance only under `abilities.flying && !isPassenger()` (`Player.java:449-451` for 26.1.2; `:450-452` for 26.2). `Player.maybeBackOffFromEdge()` reaches the reported `isAboveGround()` reader only under `!abilities.flying` plus the remaining edge-probe gates (`Player.java:880-886` in both). The ordinary tick sequence checked in the report shows no intervening flight-state change. Thus the predicates are mutually exclusive on the reviewed path, and r4 properly states that this path does not establish a same-tick reset consequence.
- The report preserves the valid direct movement consumer chain and matching bounded source observations, while leaving other fall-distance readers/writers and the rest of the pair open. Its artifact-payload availability caveat remains the same as the already-audited r3 report; no claim was expanded.

## Limits

Acceptance covers only the corrected bounded report and this changed claim. It does not establish whether another movement path reads the reset state, close remaining tick/dependency coverage, prove an intermediate onset, or freeze 26.1.2 → 26.2.
