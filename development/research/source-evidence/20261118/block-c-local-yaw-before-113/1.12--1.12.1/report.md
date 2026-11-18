# Source comparison: 1.12 → 1.12.1 — ordinary player yaw

Status: bounded yaw operation comparison complete; no independent yaw difference found. This is a source-lane report, not a full-pair coverage freeze.

## Authenticated inputs

- `1.12`, Ornithe Feather: current canonical tree `decompiled_minecraft/1.12/ornithe-feather/`; provenance-manifest SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- `1.12.1`, Ornithe Feather: current canonical tree `decompiled_minecraft/1.12.1/ornithe-feather/`; provenance-manifest SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` (matches supplied identity).
- Authenticated focused files are byte-identical at both endpoints: `Entity.java` SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `LivingEntity.java` `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`; `GameRenderer.java` `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb`. Each file hash matches the source map in its canonical provenance manifest.

## Compared operations

- `net/minecraft/client/render/GameRenderer.java`, direct calls to `player.updateLocalPlayerCamera(h, i * j)` (lines 844–868), are byte-identical.
- `net/minecraft/entity/Entity.java`, `updateLocalPlayerCamera(float yaw, float pitch)` (lines 307–318), is byte-identical. It saves old yaw to float `g`, assigns current yaw using `(float)(this.yaw + yaw * 0.15)`, updates pitch/clamps it, then adds the current-minus-old yaw delta to `lastYaw`; no yaw wrap occurs in this operation.
- `net/minecraft/entity/living/LivingEntity.java`, `turnHead`'s ordered `< -180.0F` then `>= 180.0F` prior-yaw adjustment loops (lines 1715–1722) and the first ordinary dry ground/air relative consumer (`moveRelative` -> `updateVelocity` at lines 1478–1494) are byte-identical. `Entity.updateVelocity` retains the same yaw-dependent horizontal terms as 1.12.

## Result and boundary

No difference was found in the scoped direct yaw argument, float arithmetic, current/prior update order, wrap guards, or first dry ordinary ground/air yaw consumer. No movement-kernel change is present in these authenticated focused files.
