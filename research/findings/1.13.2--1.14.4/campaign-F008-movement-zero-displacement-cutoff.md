# F008: Resolved displacement cutoff

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 578–599.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/Entity.java`, `move`, lines 463–468.

Difference: After sneak-edge preprocessing, 1.14.4 calls the collision solver but sets the shape and derives position only when the resolved vector squared length is greater than `1.0E-7`. 1.13.2 enters axis resolution for any nonzero requested component and later updates position from shape without this squared-length cutoff.

Movement meaning: All non-noClip Entity.move callers can reach this behavior; piston preprocessing and noClip have separate guards.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F008-movement-zero-displacement-cutoff.md).
