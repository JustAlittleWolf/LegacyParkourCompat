# F-001: Minor horizontal collision no longer always stops local sprinting

- Older version A: 1.17.1
- Newer version B: 1.18.2
- Mechanic / coverage slice IDs: T-SPRINT
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.17.1, 1.18.2]
- Runtime validation: not performed

## Paired evidence

A LocalPlayer#aiStep lines 715-724, SHA c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812. B lines 708-718, SHA 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095. B classifier LocalPlayer 998-1014; writer Entity#move 543-584.

## Source-level difference

A stops for any horizontal collision. B adds horizontalCollision && !minorHorizontalCollision, exempting minor local collisions when other terms are false.

## Reachability and dependencies

LocalPlayer#aiStep -> movement -> Entity#move flags -> next sprint gate; solver/input closure open.

## Consequence and uncertainty

Sprint may persist in B where A stops it; later speed/position effect is inferred, not measured.

## Handoff

Source discovery only; implementation deferred.

