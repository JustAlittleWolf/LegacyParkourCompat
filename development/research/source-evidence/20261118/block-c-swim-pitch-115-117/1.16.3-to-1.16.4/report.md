# Swim-pitch vertical adjustment, 1.16.3 → 1.16.4

## Result

No source difference was found in the bounded ordinary-player swimming pitch-to-Y adjustment or the examined look-vector and inherited travel dependencies. The exact `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` decompiled source bytes are identical between the two releases. This is a no-difference result for this operation, not full-pair movement coverage.

## Source identity and integrity

Both trees are the local exact-release `mojmap` outputs. Each `.decompile-provenance.json` has pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess` and runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`.

- 1.16.3 provenance manifest SHA-256: `513271804EAD6757345D305717E7E0BFC1641434C35A3B254F287BD8FD281498`.
  - Cached client pin: `https://piston-data.mojang.com/v1/objects/1321521b2caf934f7fc9665aab7e059a7b2bfcdf/client.jar`, SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`.
  - Cached official Mojang client mappings pin (`client.txt`): `https://piston-data.mojang.com/v1/objects/faac5028fbca3859db970cc4ca041aeec55f6d9d/client.txt`, SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
  - Version metadata pin: `https://piston-meta.mojang.com/v1/packages/6485dd131ef68c968041a9f6fd73094b027e42e1/1.16.3.json`, SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`.
- 1.16.4 provenance manifest SHA-256: `1E04555C915527A398A2C7150679FB24D056F54BD680261E3C1DD55DA821147C`.
  - Cached client pin: `https://piston-data.mojang.com/v1/objects/1952d94a0784e7abda230aae6a1e8fc0522dba99/client.jar`, SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`.
  - Cached official Mojang client mappings pin (`client.txt`): `https://piston-data.mojang.com/v1/objects/0837de813d1a6b67e23da3c520a84e872c8d2f0e/client.txt`, SHA-256 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.
  - Version metadata pin: `https://piston-meta.mojang.com/v1/packages/596ad61fda7612d9edf8881cf81869276bdb7f82/1.16.4.json`, SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`.

Cached client and mapping file bytes were independently SHA-256 checked against the pin sidecars and provenance inputs. For each of both manifests, its `sources` hash entries match freshly computed SHA-256 for all four source files below; the corresponding source hashes are equal on the two sides:

| Relative source | SHA-256 on both sides |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

## Bounded trace

In both releases, `net/minecraft/world/entity/player/Player.java:1387–1417` overrides `travel(Vec3)`. It first saves current X/Y/Z, then enters the swim adjustment only under `this.isSwimming() && !this.isPassenger()`. `Player.isSwimming()` at `Player.java:1782–1784` additionally requires `!this.abilities.flying` and `!this.isSpectator()` before `super.isSwimming()`.

Inside the swim gate, `double g = this.getLookAngle().y`; the coefficient is selected next as `double h = g < -0.2 ? 0.085 : 0.06`. The adjustment condition is ordered and short-circuiting: `g <= 0.0 || this.jumping || !this.level.getBlockState(new BlockPos(this.getX(), this.getY() + 1.0 - 0.1, this.getZ())).getFluidState().isEmpty()`. Therefore nonpositive Y-look bypasses both later checks; jumping bypasses the block/fluid sample. The sample is at current X/Z and `Y + 1.0 - 0.1`, with those double operations in that order. If the condition succeeds, it reads current velocity and writes `vec32.add(0.0, (g - vec32.y) * h, 0.0)` through `setDeltaMovement`. For positive look-Y, an unoccupied sample and no jump skip this pre-travel adjustment. A value below `-0.2` selects `0.085`; otherwise it selects `0.06`. The selection precedes the short-circuit condition.

`Entity.getLookAngle()` (`Entity.java:1700–1702`) calls `calculateViewVector(this.xRot, this.yRot)`, passing the current rotations as floats (no interpolation). `calculateViewVector` (`Entity.java:1243–1251`) converts float pitch `xRot` and float yaw `yRot` to radians with float intermediates, obtains float `cos`/`sin` values in this order: yaw cosine `j`, yaw sine `k`, pitch cosine `l`, pitch sine `m`, then constructs `new Vec3(k * l, -m, j * l)`. The Y component `-m` is computed as a float and widened to the `Vec3` double component; `g` is then read as double.

`Mth.sin(float)` (`Mth.java:25–27`) returns `SIN[(int)(f * 10430.378F) & 65535]`: multiplication uses the float literal, conversion to `int` truncates toward zero, then the index is masked to 16 bits. `Mth.cos(float)` (`Mth.java:29–31`) uses the same table and scale with `+ 16384.0F` before integer conversion and masking. The static initializer fills the 65,536-entry sine table from `Math.sin(ix * Math.PI * 2.0 / 65536.0)` and casts each result to float (`Mth.java:10–15` in both files).

After the pre-adjustment, the ordinary non-flying branch calls `super.travel(vec3)`; the player flying/nonpassenger branch is separate. The inherited `LivingEntity.travel` (`LivingEntity.java:1914+`) contains the locally controlled/effective-AI movement paths and invokes `move(MoverType.SELF, this.getDeltaMovement())` for water travel (line 1947), lava travel (1962), and ordinary travel (2065). `Player.travel` then records movement statistics (`Player.java:1416`). These inherited calls and the view/trig routines have identical source hashes across the compared versions.

## Compared slices

- `Player.travel`, complete override body including the surrounding flying branch and trailing movement-statistics call: 1.16.3 and 1.16.4 `Player.java:1387–1417`.
- Player swimming eligibility used by the outer gate: both `Player.java:1782–1784`.
- Current-look vector method and trig helper/table: both `Entity.java:1243–1251, 1700–1702`; both `Mth.java:10–31`.
- Inherited travel and actual movement call sites: both `LivingEntity.java:1914–2065`.
- Dependency closure for this operation: closed for the examined current-pitch look-Y calculation and inherited travel calls; the relevant source files are byte-identical. No broader tick inventory is claimed.
