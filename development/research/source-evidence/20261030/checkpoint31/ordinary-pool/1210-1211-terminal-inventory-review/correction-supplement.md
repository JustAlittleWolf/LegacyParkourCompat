# Correction supplement proposal: 1.21.0 → 1.21.1 terminal inventory

This immutable supplement proposes corrections to `terminal-inventory.md`; it does not replace or edit the original. Basis: its pinned source audit reports complete equal 5,363-file Java path sets with exactly 17 changed hashes; the six changed callback/entity paths have the separate bounded ordinary-valid-state review; default packaged movement-selection resources have a paired report and independent review. No pair freeze is inferred.

## Ordered terminal corrections

Place the pre-travel sampled-world-state row before local input/control. In 1.21.0 `Player.tick` invokes `updateIsUnderwater` before `super.tick` (`Player.java:249–276`); `LocalPlayer.aiStep` and `input.tick` execute later (`LocalPlayer.java:644, 664`) and then delegate through `super.aiStep` (`:804`) to travel. If needed, separate the early player underwater sample from the rest of `Entity.baseTick` sampling to preserve exact ordering.

Add a post-travel terminal for entity pushes. `LivingEntity.aiStep` calls `pushEntities()` after travel (`LivingEntity.java:2705`). On the client, the method queries player candidates and runs `doPush` (`:2746–2749`), while other player ticks can invoke `LocalPlayer.push`, changing local-player delta movement. The implementation is unchanged across these endpoints, so list this as a source-equal terminal rather than an open version-difference dependency.

## Dependency and closure corrections

With the complete Java path/hash comparison, unchanged providers and branches do not remain open solely because the transitive caller/provider graph was not re-enumerated. Record them as source-equal for this pair under equal relevant runtime state. The changed members are confined to the 17 audited paths; preserve the special unresolved stale/mismatched shulker block-entity legality/reachability edge, because a shulker support/collision shape reads its block entity.

The 1.21.0/1.21.1 packaged default resource comparison is closed under its stated assumptions. Cite `.native-source/20261030/ordinary-pool/1210-1211-resources/report.md` (SHA-256 `59b727aa2e74d6c71927cad0b634cbfac2689fda09ebcda1e0b751b149685ec0`) and `.native-source/20261030/ordinary-pool/1210-1211-resources-review/review.md` (SHA-256 `bd1ffd84267b58121a7c1fd3b1c1a171c6b2628ff973ec4043b41b92c87595e4`). Remove the default-resource pair comparison from the open list. Retain custom packs/reloads and relevant received world/state values as equal-effective-input assumptions.

The four changed `onRemove` methods run on the logical server and can change authoritative neighbor/comparator results and therefore later received world state. Keep that as an external-input boundary if server-produced state equality is part of the question. Under equal received block states, the bounded closure finds no direct client collision/support response. The changed command/chat parser paths are likewise not ordinary client-tick terminals; any resulting changed player/world state is an external input.

Phrase attributes, effects, equipment, abilities, keys/configuration, entity candidates, and packet values as state/input premises when comparing movement consumers. Their unchanged Java consumers do not themselves leave pair-specific source dependencies open.

## Changed-path detail and citation fix

In the `BlockEntity.java` row, also classify the `fillCrashReportCategory` Name-formatting extraction as equivalent crash-report formatting, outside movement behavior.

The closure at `.native-source/20261030/1210-1211-block-callbacks/closure.md` has SHA-256 `b7dbc5af7fcad4b3e8326ecbc2888c7ec8e72f593d153c1180f57394bacda5e0`. Its independent audit at `.native-source/20261030/ordinary-pool/1210-1211-block-callbacks-review/audit.md` has SHA-256 `ea15f281abe0c4b0412f57eb22ff94dca4caf8709b072207b752c1d97ae7d2df`; currently the inventory attributes the closure hash to the audit.

The prior DDA, callback-family, and phase-order closures are bounded comparisons of 1.21.2/1.21.3 with 26.2 in named slices. Keep the report’s non-transfer caveat, and make the 26.2 comparison explicit.
