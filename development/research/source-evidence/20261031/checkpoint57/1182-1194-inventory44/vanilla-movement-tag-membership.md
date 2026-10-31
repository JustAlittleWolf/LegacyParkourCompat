# Vanilla movement tag membership closure

Scope: vanilla tag definitions and vanilla data-provider membership in the six exact Mojmap source roots. This closes the movement-relevant vanilla tag inputs under the fixed vanilla block-state assumption. It does not inspect runtime datapack/resource-pack overrides.

## Exact tags and movement consumers

| Resource path | Tag ID | Exact direct vanilla consumers | Eligibility |
|---|---|---|---|
| `data/minecraft/tags/blocks/climbable.json` | `minecraft:climbable` (`BlockTags.CLIMBABLE`) | `LivingEntity.onClimbable`; `Entity.move` climb branch | Eligible direct player movement predicate |
| `data/minecraft/tags/blocks/soul_speed_blocks.json` | `minecraft:soul_speed_blocks` (`BlockTags.SOUL_SPEED_BLOCKS`) | `LivingEntity.onSoulSpeedBlock`; Soul Speed movement-speed modifier path | Eligible player movement speed predicate, when enchant/effect exists |
| `data/minecraft/tags/blocks/fences.json` | `minecraft:fences` (`BlockTags.FENCES`) | `Entity.getOnPosLegacy` support-position fallback | Eligible indirect movement support query |
| `data/minecraft/tags/blocks/walls.json` | `minecraft:walls` (`BlockTags.WALLS`) | `Entity.getOnPosLegacy` support-position fallback | Eligible indirect movement support query |

`BlockTagsProvider` in 1.18.2–1.19.2 and `VanillaBlockTagsProvider` in 1.19.3–1.19.4 encode the vanilla generated membership. The four tags retain their membership for block IDs that existed in 1.18.2. Later vanilla provider additions to fence/wall membership refer to later-added block IDs, which cannot occur in old map states and therefore do not change old-map movement. Soul Speed remains soul sand and soul soil. Climbable retains the same era-existing ladder/vine/scaffolding members.

The resource payloads are therefore not an unresolved dependency for vanilla old-era block states: the exact source providers establish their generated membership. Runtime overrides remain outside this vanilla source closure. No arbitrary datapack/custom-server producer evidence is included.

## Exact source hash ledger

All hashes below were recomputed from the local Mojmap source files and correspond to the ready-marker source manifests already verified by this inventory.
