# Independent source trace — swim-pitch vertical, 1.21.6 → 1.21.7

## Frozen source identity and input pins

Host/source workflow: Windows PowerShell; read repository `AGENTS.md`, `README.md`, `development/workflows/orchestration/WORKFLOW.md`, `development/workflows/discovery/WORKFLOW.md`, `development/workflows/discovery/source/WORKFLOW.md`, and `buildSrc/README.md`. Exact-source lane only; no wiki, product implementation, backlog, Git history, network, generation, build, tests, or runtime.

Canonical read-only roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.21.6/mojmap/` and `.../1.21.7/mojmap/`. Both have `.decompile-provenance.json`; each manifest's targeted source hashes match the bytes on disk. Mapping identity is the official Mojang client mapping (`client.txt`) surfaced as Mojmap. Release-specific manifests pin the exact release metadata, client, and mappings:

| Release | Manifest SHA-256 | Exact release metadata SHA-256 | Client JAR SHA-256 | `client.txt` SHA-256 |
|---|---|---|---|---|
| 1.21.6 | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` | `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd` | `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` |
| 1.21.7 | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` | `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a` | `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` | `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50` |

Both manifests state `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`; runtime `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. The official exact metadata URLs in the manifests end `/1.21.6.json` and `/1.21.7.json`; artifact URLs are the pinned `piston-data.mojang.com/v1/objects/.../client.jar` and `.../client.txt` objects represented by the hashes above.

All source pins below match in the 1.21.6 and 1.21.7 manifests and were re-hashed from each actual file:

| Mojmap source identity | SHA-256 (both releases) |
|---|---|
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/util/Mth.java` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |

The complete four source files are byte-identical across 1.21.6 and 1.21.7, so their enclosing methods and context have no patch difference in this source pair.

## Frozen behavior trace

**Player pre-super local adjustment.** `Player.travel(Vec3)` is `Player.java:1438–1460` in both releases. Passenger handling is the first gate: passengers call `super.travel` directly. Only the `else` (nonpassenger) path checks `isSwimming()` (`1439–1443`). Player's `isSwimming()` override at `1774–1776` is `!abilities.flying && !isSpectator() && super.isSwimming()`, so this swim adjustment cannot overlap with the later flying branch.

When swimming, the code reads `double lookY = this.getLookAngle().y` (`1443`), then selects `0.085` when `lookY < -0.2`, otherwise `0.06` (`1444`). It adjusts only if `lookY <= 0.0`, or `jumping`, or the fluid state at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty (`1445–1447`). Inside that gate it reads the then-current delta movement (`1448`) and writes `delta + (0.0, (lookY - delta.y) * coefficient, 0.0)` (`1449`). This is the player's own direct Y-velocity adjustment before superclass travel; it is distinct from inherited fluid-falling logic.

**Authoritative look angle and exact trig.** `Entity.getLookAngle()` at `Entity.java:2356–2358` directly calls `calculateViewVector(this.getXRot(), this.getYRot())`; it does not use partial-tick interpolation. The called method at `1746–1754` converts pitch and negative yaw to float radians (`$$0 * (float)(Math.PI / 180.0)` and `-$$1 * (float)(Math.PI / 180.0)`), computes float cosine/sine values in this order, and returns `new Vec3(sin(-yaw) * cos(pitch), -sin(pitch), cos(-yaw) * cos(pitch))`. Therefore swim `lookY` is the double `Vec3.y` produced by negating the float `Mth.sin` result for the current X rotation converted to radians.

`Mth.java:28–35,48–50` defines `SIN_SCALE = 10430.378F`, constructs `SIN` as 65,536 float entries `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, and `sin(float)` returns `SIN[(int)(radians * 10430.378F) & 65535]`. The multiplication is float; cast truncates to int before the 16-bit mask/table access. These trig and angle methods are also byte-identical across the pair.

**Read → actual movement.** The local velocity write precedes the `super.travel($$0)` call (`1458` when not flying; `1455` in the separate flying path). `LivingEntity.travel` at `LivingEntity.java:2245–2250` dispatches affected non-standable water/lava entities to `travelInFluid`; for water, `travelInFluid` at `2301–2330` calls `moveRelative`, then `move(MoverType.SELF, getDeltaMovement())` (`2323`), and only afterward applies horizontal damping, vertical `0.8F` scaling/falling adjustment, and stores the resulting velocity (`2324–2330`). Thus, in the ordinary eligible swimming-in-water path, the Player-local Y velocity reaches the real movement call before inherited water velocity damping/fall adjustment. The non-water/unsupported route is not needed to explain the direct Player write; source shows superclass call remains after it in either case.

**Separate flying Y overwrite.** Still in the nonpassenger branch, after the swim block, `abilities.flying` saves the current Y delta (`1453–1454`), calls superclass travel, then overwrites Y with saved-Y times `0.6` (`1455–1456`). The nonflying alternative calls superclass travel once (`1458`). Given Player's swim predicate excludes flying, this is a distinct operation and not evidence of a swim-pitch delta.

## Independent source verdict, frozen before author-report access

No 1.21.6 → 1.21.7 source difference is present in this operation or its traced angle/trig/inherited-water movement dependencies. `Player.travel`, `Entity.getLookAngle` / `calculateViewVector`, `Mth.sin`, and the traced `LivingEntity.travel` path have matching complete-file source identities in both exact mapped releases. This pair supports no new movement change for the scoped swim-pitch vertical operation. The trace proves source parity only; it does not establish runtime parity or a patch boundary outside this pair.

## Independent audit of the sealed source report

After freezing the source trace above (pre-report trace SHA-256 `601d256c15724c4a4d224907bcB431419045996591aa52b24bb25258caebbb06`), opened only the assigned author report. Its expected SHA-256 is `684b74931ad57e29b086af65c878c5da043ae5941307d2f326bbab74c4ec51ea`, and the read bytes matched that identity.

The report's no-difference conclusion, release/mapping pins, manifest identities, and four source hashes match the independent checks. I additionally verified `Entity.getXRot()` returns stored `xRot` directly and the finite setter clamps it (`Entity.java:3705–3715`); the player overrides `isSwimming()` to exclude flying (`Player.java:1772–1776`); and neither `Player` nor `LivingEntity` overrides `move(MoverType, Vec3)`, so the traced call reaches `Entity.move` (`Entity.java:663+`). The report's `Player.travel` citation says `1438–1465`; the method body itself closes at line 1460, with the next `canGlide` method beginning at 1463. This range overrun is only a citation imprecision; its cited body and conclusion are accurate.

**Verdict: ACCEPT the no-source-difference claim for this bounded operation.** The direct swim Y writer is present with identical code in both versions, follows the same gates and exact current-angle/table path, and precedes the inherited movement call. The flying overwrite remains a separate post-super operation and cannot overlap with `isSwimming()` for a player. No finding or numeric witness is supported for 1.21.6 → 1.21.7 here. Scope is this operation and traced dependencies only; no runtime parity claim.
