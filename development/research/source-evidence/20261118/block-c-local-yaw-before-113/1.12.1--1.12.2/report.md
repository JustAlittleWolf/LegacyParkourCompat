# Source comparison: 1.12.1 → 1.12.2 — ordinary player yaw

Status: bounded yaw operation comparison complete; no independent yaw difference found. This is a source-lane report, not a full-pair coverage freeze.

## Authenticated inputs

- `1.12.1`, Ornithe Feather: current canonical tree `decompiled_minecraft/1.12.1/ornithe-feather/`; provenance-manifest SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- `1.12.2`, Ornithe Feather: current canonical tree `decompiled_minecraft/1.12.2/ornithe-feather/`. Source-manifest sidecar SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; provenance metadata SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`.
- Relevant files were checked against each endpoint's exact source map/sidecar before examining their bodies. Focused source hashes are equal between endpoints: `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`, `LivingEntity.java` `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`, `GameRenderer.java` `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`.

## Compared operations

- `net/minecraft/client/render/GameRenderer.java`, direct calls to `player.updateLocalPlayerCamera(h, i * j)` (lines 844–868), are byte-identical.
- `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(float yaw, float pitch)` (lines 307–318), is byte-identical: save current yaw in float `g`; assign `(float)(this.yaw + yaw * 0.15)`; then after pitch update/clamp, add `(this.yaw - g)` to `lastYaw`. The direct camera operation does not wrap yaw.
- `net/minecraft/entity/living/LivingEntity.java`, ordered `< -180.0F` then `>= 180.0F` loops adjusting `lastYaw` (lines 1715–1722), and the first ordinary dry ground/air relative consumer (`moveRelative` -> `updateVelocity` at lines 1478–1494) are byte-identical. The horizontal yaw transform in `Entity.updateVelocity` is unchanged.

## Result and boundary

No difference was found in the scoped direct yaw argument, float arithmetic, current/prior update order, wrap guards, or first dry ordinary ground/air yaw consumer. No movement-kernel change is present in these authenticated focused files.
