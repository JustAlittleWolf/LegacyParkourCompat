# Independent source verdict: respawn player movement flags (revision 2)

**Verdict: ACCEPT.** This supersedes the prior rejection, which was limited to a bad 1.19 line reference.

- Reviewed draft: `.native-source/20261030/1182-1194-inventory44/findings/1.19.2--1.19.3/respawn-player-movement-flags-copy.md`, SHA-256 `06E01EC0FE826387B91D5AF9FA52768B39B97798AC9F6897B33F081C156D5376` (verified).
- Corrected 1.19 `LivingEntity.isFallFlying()` reference: lines 3045–3047; the canonical source contains the declaration, `return this.getSharedFlag(7)`, and closing brace at those lines. The cited 1.19.3 range 3063–3065 remains correct.
- The remaining source findings and scope are unchanged and supported: 1.19/1.19.1/1.19.2 copy all entity-data values unconditionally, while 1.19.3 copies nondefault entity data only under keep bit 2, separately from attributes under bit 1. The shared sprint bit and `LivingEntity.travel` water slowdown provide a reachable player movement consequence for equal underwater forward input. `LocalPlayer` initialization/input processing does not erase the distinction before travel. Keep server-side keep-bit production unasserted and the claim limited to player movement effects.

## Preserved source identity

The exact Mojmap/Vineflower source roots and manifest identities are unchanged and verified against their `mojmap.ready.json` files:

- 1.19: `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`
- 1.19.1: `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`
- 1.19.2: `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2`
- 1.19.3: `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`

The independent SHA-256s for all reviewed source files, including the additional `LocalPlayer.java` reachability evidence, remain recorded in the superseded verdict at `respawn-player-movement-flags-copy.verdict.md` and were not altered by this citation-only draft revision.
