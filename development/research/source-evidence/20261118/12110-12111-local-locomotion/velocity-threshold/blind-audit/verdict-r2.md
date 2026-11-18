# Corrective receipt: source file digest in comparison-r1

## Binding and correction

Reviewed additive author artifact `source/comparison-r1.md` at SHA-256 `6550a3a37cd51e48072fc9395b408fadbe3f41ee774650e2585c2ab10315fa6a`. Its 1.21.11 `Entity.java` digest on line 10 is truncated to 63 hexadecimal characters and ends in `...29cf18a`.

The exact corrected 1.21.11 SHA-256 is:

`32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`

This value was recomputed from `decompiled_minecraft/1.21.11/mojmap/net/minecraft/world/entity/Entity.java`, matched the `net/minecraft/world/entity/Entity.java` entry in `decompiled_minecraft/1.21.11/mojmap.sources.sha256`, and matches the independent frozen record. For comparison, the 1.21.10 file hash is `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`; actual source and manifest entry agree.

Prior review identities were rechecked and remain unchanged:

- Original author comparison: `eec89743cb57c69122cb6616da2fcbe2f51fb9bd692a001a0ba342d3ba7e614a`.
- Independent freeze: `60a48ca6772a7843bbefd712bb8dc39e0c233ab58d7fee22a0d9a168d631960f`.
- Initial review: `7dbd070d72a92cedae2291ac9d1b711e367768505fba6afecf943e6583185f2c`.
- Additive review: `48be9ab92f23d4e19cc73c85362d74049576eb77831ca36cda73ec9b362f2ec2`.

## Ten method-span rechecks

I re-extracted each exact member from both canonical Mojmap files (five spans per release) and recomputed its UTF-8 member SHA-256. Each matches the hashes in the additive comparison:

| Member | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
|---|---|---|
| `Entity.move(MoverType, Vec3)` | `cd092568f6af74a10a4c9dfe1d9e53b70582a50ad57625ff9132c9ae9cdaf697` | `cd092568f6af74a10a4c9dfe1d9e53b70582a50ad57625ff9132c9ae9cdaf697` |
| `LivingEntity.handleRelativeFrictionAndCalculateMovement(Vec3, float)` | `625ccacd48603ff77e42a7aaa54de632d9c743336d0eda89be8bacf59ecab392` | `625ccacd48603ff77e42a7aaa54de632d9c743336d0eda89be8bacf59ecab392` |
| `Entity.setDeltaMovement(Vec3)` | `64a7ca0ab043bb5fae6fd46dbc430a9f15103c37c3aef38aa9f6d385a3dbeca2` | `87ac544caafe5b8f6155365ca6d6556a6ac3f27301ce70065dc3c4b64af074b2` |
| `Entity.addDeltaMovement(Vec3)` | `7a9bf6bfdc8290cffb1ffe643b86761e6d46ad87bc25cd10f368291be70107c9` | `10d0dd2e30ca022dcbd4fd8b9d4ec06177cc6eadb78017c5e51774c4d4b06d46` |
| `Entity.setDeltaMovement(double, double, double)` | `659c50e81d4b412a9ba87f2693aa43d60712d0a991d6217549d4bb0498125c20` | `659c50e81d4b412a9ba87f2693aa43d60712d0a991d6217549d4bb0498125c20` |

**Verdict:** The corrected file digest does not change the accepted additive evidence or bounded conclusion. For finite vectors and the same fixed collision/provider/callback inputs, the source-identical move/helper writer sequence remains equivalent: stuck reset, clip comparison, blocked X/Z component reset preserving Y, conditional fall-on callback, then horizontal speed-factor write. The 1.21.11 `isFinite()` guards in the Vec3 setter overloads remain a separate non-finite behavior delta. No reachability or parity claim is made for non-finite values, and this receipt makes no wider movement inference.

Only this corrective receipt was added; earlier frozen and review files were preserved byte-for-byte.
