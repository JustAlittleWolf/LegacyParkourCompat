# Direct pitch setter source record: 1.14 → 1.14.1

## Result

The exact Yarn-mapped ordinary direct look operation is `Entity.changeLookDirection(double, double)` in both releases. The operation bytes are identical; this pair shows no source change to the direct pitch setter. The lane label `Entity.turn` is not the Yarn name in these local sources. This record covers the direct setter, its direct caller context, and the next live player Elytra travel read only.

## Source identity and local pins

Canonical read-only roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14\yarn\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.1\yarn\`

Both roots contain `.decompile-provenance.json`; the manifest source SHA-256 values below were checked against the corresponding file bytes.

| Release | Yarn mapping pin | Intermediary pin | Raw client pin |
|---|---|---|---|
| 1.14 | `yarn-1.14+build.21-v2.jar` — `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` | `intermediary-1.14-v2.jar` — `45e354d58c74473865054faa62a229e30f46ba2019000affdb3fb748df3fde17` | `client.jar` — `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` |
| 1.14.1 | `yarn-1.14.1+build.10-v2.jar` — `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` | `intermediary-1.14.1-v2.jar` — `486f4e72a87d697c5d9e155a49158bec28117faa73e3f1201c3f8a616b42569d` | `client.jar` — `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` |

Manifest files:

- 1.14 `.decompile-provenance.json`: SHA-256 `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`.
- 1.14.1 `.decompile-provenance.json`: SHA-256 `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`.

Artifact pin sidecars are in `.minecraft-decompile-cache/<version>/client.jar.pin.json` and `.minecraft-decompile-cache/yarn/{yarn-<version>+build.<n>-v2.jar,intermediary-<version>-v2.jar}.pin.json`. Their URLs and artifact SHA-256 values agree with each decompile provenance manifest. No source generation or network lookup was performed.

## Setter and call context

In both versions, `net/minecraft/entity/Entity.java:313–325` is byte-for-byte identical (source SHA-256: 1.14 `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a`; 1.14.1 `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`). For finite `d` and `e`, its operation order is:

1. `f = e * 0.15` and `g = d * 0.15`, with both temporaries `double`.
2. `pitch = (float)(pitch + f)`; float pitch is promoted for the double addition, then narrowed to float.
3. `yaw = (float)(yaw + g)`.
4. Clamp the newly stored float pitch to `[-90.0F, 90.0F]`.
5. Independently set `prevPitch = (float)(prevPitch + f)` from its own old value, then clamp it to the same float bounds.
6. Independently update `prevYaw`; if mounted, call `vehicle.onPassengerLookAround(this)`.

Thus the current and previous pitch are separately accumulated and clamped; previous pitch is not copied from current pitch. The method has no Elytra-specific gate.

The only direct source call found in either source root is `client/Mouse.java:214`, `this.client.player.changeLookDirection(j, k * n)`. This is caller-context evidence only; mouse/device input calculation is outside this slice. The caller line is present in both releases and its source hashes are: 1.14 `8e9595a7306be13b81de1960e68f3ad883d7529fe1739b10248b0afd6413b2de`; 1.14.1 `dedc0f972addc92ad335ea86210f71c4f3de56dcbebb81958cc509189b94d742`.

## Live active Elytra next-move read

`LivingEntity.tickMovement` calls `this.travel(new Vec3d(...))` at `LivingEntity.java:2204` in both versions. The player travel path delegates to `super.travel(arg)` (`PlayerEntity.java:1379` and `1386` in both releases). In `LivingEntity.travel`, the active `isFallFlying()` branch at line 1774 reads the **current** pitch at line 1781 as `float l = this.pitch * (float) (Math.PI / 180.0);`; the resulting angle feeds cosine and sine terms that update velocity before `move`. It does not use `prevPitch`. Therefore, for a player who is actively fall-flying when that next movement call reaches the branch, the current pitch produced by the direct setter is a live input to the next Elytra movement step.

The compared `travel` slice (`LivingEntity.java:1763–1817`) is identical across the pair. Source SHA-256: 1.14 `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6`; 1.14.1 `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`. `PlayerEntity.java` is byte-identical across the pair (SHA-256 `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`).

## Pair conclusion

No 1.14 → 1.14.1 difference was found in the direct pitch setter, independent previous-angle handling, direct call site, or active-Elytra next movement read. This is a bounded source result for the assigned operation, not a full-pair movement coverage or runtime-parity claim.