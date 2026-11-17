# Independent source audit: player-contact r2

Verdict: **PASS for the confirmed restitution difference and the explicitly bounded source dispositions; the pair remains partial and unfrozen.** The result is bound to `build/later-source-20261116/26.1.2--26.2/player_contact/coverage-r2.md`, SHA-256 `ffe7851867b19a2aab96024cc6b0a017f4a22005d971072cba62c7ad24b7b631`.

## Evidence identity

Both endpoint ready markers identify the requested exact releases with `unobfuscated` mappings. Provenance file hashes match the report: 26.1.2 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; 26.2 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`. Each source-manifest hash matches its ready marker. Relevant `Entity.java` file hashes and ledger entries match the report for both endpoints; 26.2 `LivingEntity.java` hash and ledger entry also match. No shared source was changed.

## Audited claims

- In 26.1.2 `Entity.move`, normal collision resolution clips movement, zeros collided horizontal delta-movement components, and on simulated vertical collision calls `updateEntityMovementAfterFallOn`; no restitution method or bounciness hook exists in the examined 26.1.2 entity sources.
- In 26.2, `Entity.move` invokes `restituteMovementAfterCollisions` only when `canSimulateMovement()` and a moved vertical collision or horizontal collision is present. `noPhysics` exits before this path. The routine reverses/scales collided X/Z using entity restitution; for vertical collision it uses entity restitution, and for downward collision may choose the greater block restitution after gravity, entity-suppression, and block-tag gates. The Y calculation uses movement/gravity compensation and air drag. A positive effective restitution with a horizontal collision, or the qualifying vertical case, sets `GameEvent.BOUNCE` and position sync.
- The LocalPlayer route is reachable: `LivingEntity` movement calls virtual `move`; `LocalPlayer.move` delegates to `super.move`; `canSimulateMovement` follows local-instance authority, and `Player.isLocalClientAuthoritative` returns `isLocalPlayer()`. A descending local player on a positively restitutive block can therefore reach the vertical path when its stated gates pass. A horizontal living-player bounce requires nonzero `BOUNCINESS`; 26.2 registers it with default 0.0/range 0.0–1.0, includes it in LivingEntity's attribute set, and `LivingEntity.getEntityBounciness` reads it. The endpoint sources show no corresponding 26.1.2 bounciness member or attribute.
- The report correctly limits the finding to endpoint evidence and does not claim profile applicability, intermediate cutover, measured trajectory, or full-pair closure. The retained no-difference results are bounded dispositions, not comprehensive player-contact coverage.

## Minor precision note

The report's abbreviated inheritance chain skips `Avatar`: the exact chain is `LocalPlayer → AbstractClientPlayer → Player → Avatar → LivingEntity → Entity`. This does not affect the reachability conclusion.

## Limits

This audit covers the r2 restitution/bounciness claims and its explicitly retained bounded dispositions only. It does not audit full tick coverage, other contact/input/travel areas, intermediate releases, implementation, or runtime parity. Pair coverage remains partial/incomplete/not frozen.
