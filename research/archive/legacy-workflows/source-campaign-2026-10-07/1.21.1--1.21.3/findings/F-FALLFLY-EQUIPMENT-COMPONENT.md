# F-FALLFLY-EQUIPMENT-COMPONENT — component-based glide eligibility

- Older version A: 1.21.1, Mojmap.
- Newer version B: 1.21.3, Mojmap.
- Mechanic / coverage slice IDs: player movement; S-FALLFLY-ELIGIBILITY, INV-MODIFIERS, INV-EXTERNAL.
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: modern-only mechanic
- First changed release: unknown within (1.21.1, 1.21.3]
- Runtime validation: not performed
- Scope disposition: the generalized `GLIDER` item-component path is recorded as a real B-side source change, but is not eligible legacy movement behavior: A has no `DataComponents.GLIDER`, and a B-only component/equipment combination has no A-era counterpart. This is not a claim that ordinary Elytra movement differs.

## Paired evidence

The original verified artifact records are in the pair manifest: A source manifest `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, artifact manifest `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`; B source manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, artifact manifest `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`.

### A — 1.21.1

- Evidence artifact record ID: A-original; publication status: original-verified.
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.21.1/mojmap`.
- Evidence manifest path / SHA-256: `mojmap.sources.sha256` / `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.
- Original artifact-manifest path / SHA-256: `artifacts.sha256` / `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486`.
- `Player.tryToStartFallFlying`, `net/minecraft/world/entity/player/Player.java`, lines 1529-1535, SHA-256 `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; it requires not grounded, not already gliding, not in water, no levitation, and an Elytra in the chest slot passing `ElytraItem.isFlyEnabled`.
- `LocalPlayer.aiStep`, `net/minecraft/client/player/LocalPlayer.java`, lines 741-745, SHA-256 `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583`; the local input path sends START_FALL_FLYING only after its Elytra checks and `tryToStartFallFlying()` succeed.
- `ElytraItem.isFlyEnabled`, `net/minecraft/world/item/ElytraItem.java`, lines 19-20, SHA-256 `aafc9e310847fccd7ffffca5773d7df8b55285a940c185e95096bacbbee2605f`; the stack must have damage below max damage minus one.
- `Items.ELYTRA`, `net/minecraft/world/item/Items.java`, line 883, SHA-256 `cdb2e4fce08c1543caf96bf32c43383a2e1cdb214ecc50fe883669bf4786924e`; registered as an `ElytraItem` with durability 432.
- `ServerGamePacketListenerImpl.handlePlayerCommand`, lines 1432-1434, SHA-256 `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10`; the START_FALL_FLYING command calls `tryToStartFallFlying()` and stops flight when it returns false.
- `DataComponents.java`, `net/minecraft/core/component/DataComponents.java`, SHA-256 `84485620f0eb60ac286bebac3c8241a6ec0d70bd3707cb8ceb3e30eda38b6302`, has no `GLIDER` component; verified by the exact A-side source search. No A-side glide eligibility path reads such a component.

### B — 1.21.3

- Evidence artifact record ID: B-original; publication status: original-verified.
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.21.3/mojmap`.
- Evidence manifest path / SHA-256: `mojmap.sources.sha256` / `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`.
- Original artifact-manifest path / SHA-256: `artifacts.sha256` / `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf`.
- `Player.tryToStartFallFlying`, `net/minecraft/world/entity/player/Player.java`, lines 1525-1532, SHA-256 `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; the same command calls `canGlide()` and excludes water. `Player.canGlide`, lines 1471-1472, also excludes creative flight.
- `LocalPlayer.aiStep`, `net/minecraft/client/player/LocalPlayer.java`, lines 735-737, SHA-256 `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0`; the local jump-input path sends START_FALL_FLYING after the generalized `tryToStartFallFlying()` succeeds.
- `LivingEntity.canGlide`, `net/minecraft/world/entity/LivingEntity.java`, lines 2836-2848, SHA-256 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`; it excludes ground, passengers and levitation, then checks every equipment slot.
- `LivingEntity.canGlideUsing`, lines 3615-3622, same hash; requires `DataComponents.GLIDER`, a non-null `DataComponents.EQUIPPABLE`, the current slot to equal the component's slot, and `!nextDamageWillBreak()`.
- `DataComponents.GLIDER`, `net/minecraft/core/component/DataComponents.java`, lines 153-155, SHA-256 `47b74d16c5f2b995eee896a9aff3a3cb59fa9cd5daf88435a5a803db413fba48`.
- `Items.ELYTRA`, `net/minecraft/world/item/Items.java`, lines 958-973, SHA-256 `bc99499e18f7099aab5c15f5921d583906e6cc48a7eaad8e81d09cd26ff7b7af`; the built-in Elytra receives GLIDER and an EQUIPPABLE component for `EquipmentSlot.CHEST`.
- `ItemStack.nextDamageWillBreak`, lines 453-455, SHA-256 `46e63b700893e7d3a7e455e86bebdd2311135cf3d822038ef3906d4d890b93a9`; its durability boundary (`damage >= maxDamage - 1`) is the same boundary as A's `isFlyEnabled`.
- `ServerGamePacketListenerImpl.handlePlayerCommand`, lines 1445-1448, SHA-256 `e4ad1ac2fa323f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38`; the START_FALL_FLYING command uses the generalized player gate.

## Source-level difference

The ordinary built-in Elytra gate has matching grounds, water, levitation and durability limits at these endpoints. B additionally lets an equipped stack pass the flight-start gate when its synchronized item state carries GLIDER and EQUIPPABLE components naming the current slot; A's source accepts only the Elytra item in CHEST. B's built-in Elytra is registered with those components, while the B source API can also admit another server-supplied item/component combination.

## Reachability and dependencies

Serverbound START_FALL_FLYING -> `Player.tryToStartFallFlying` -> `canGlide` -> equipment component/slot/durability predicate -> shared fall-flying flag -> B travel's fall-flying branch. A's command reaches its item-ID/chest-slot predicate. The component and equipment data are synchronized item state; this finding does not infer custom server data or its default production beyond the B-side Java registration cited above.

## Consequence and uncertainty

Source-proven: B admits a component-qualified matching-slot stack that A's Elytra-ID/chest-only condition rejects. The additional case requires B-side equipment data with no A-side `GLIDER` component representation, so it is an explicit modern-only/out-of-scope disposition. No different trajectory for the ordinary built-in Elytra is established. Pair-wide resource and external-data inventories remain open; no gameplay consequence was runtime tested.

## Handoff

Independent source delta: generalized component-based glide eligibility, dispositioned out of scope for historical emulation. Related to S-FALLFLY-ELIGIBILITY and INV-MODIFIERS. Implementation and runtime validation are deferred.
