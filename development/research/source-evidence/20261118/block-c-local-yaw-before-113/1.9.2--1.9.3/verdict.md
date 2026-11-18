# Verdict: 1.9.2 to 1.9.3 local yaw and ordinary yaw-relative acceleration

Verdict: **NO DIFFERENCE in the assigned slice.** This is an individual adjacent-pair verdict, not a claim about other movement kernels or the full tick graph.

## Exact source evidence

Mapping family: Ornithe Feather on both sides. Canonical source roots are decompiled_minecraft/<version>/ornithe-feather/; for 1.9.4 this is the Feather source tree at the version root.

- 1.9.2 net/minecraft/client/render/GameRenderer.java:849 SHA-256 D03FB41524C7B0ECF92696AE6AA8144FDCE7A449F5E670A59B210D8F6B06A709; net/minecraft/entity/Entity.java:283 SHA-256 74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A; net/minecraft/entity/living/LivingEntity.java:1293 SHA-256 D8824F670037A88753C428C27213989FC5173B1D7C638F498F73395C145B6FD9.
- 1.9.3 net/minecraft/client/render/GameRenderer.java:850 SHA-256 E08C2034C7FD4621A76FF5A4BA26DD80E065C895BC5C0A4120D4D36141203D6B; net/minecraft/entity/Entity.java:286 SHA-256 BCD7FA2206BF8D7271102F6DA7FE2771F96DBE22EC79DAB9F2101A3E29C17EF0; net/minecraft/entity/living/LivingEntity.java:1302 SHA-256 BBB7703F18FD5DA05C4E4A43A77EA644B388E63C01D34166D308EA52054BE4E5.

## Compared operations and result

The GameRenderer focus-gated local path calls player.updateLocalPlayerCamera(h, i * j) in both smooth and ordinary branches. Mouse sensitivity/device processing is outside this verdict. The call site is unchanged in the reviewed slice.

Entity.updateLocalPlayerCamera(float yaw, float pitch) is byte-for-byte identical in the five pinned releases: save current pitch/yaw to float locals; add (float)(this.yaw + yaw * 0.15) to current yaw; set pitch from (float)(this.pitch - pitch * 0.15) and clamp pitch to [-90.0F, 90.0F]; shift lastPitch and lastYaw by each current-angle delta; notify the mount only when mount != null. The yaw path has no clamp, normalization/wrap, or finite-value check. The cast occurs after the double-literal 0.15 expression; saved and previous yaw values are floats.

For ordinary dry ground/air movement, LivingEntity.moveRelative retains the same gates and order: locally controlled or logical side for movement update; not in water or a flying player; not in lava or a flying player; not fall-flying. On ground it reads block slipperiness below the shape and uses getSpeed() * (0.16277136F / (s*s*s)); in air it uses speedInAir. It then calls Entity.updateVelocity. That method is identical across all five releases: float input-square sum; strict < 1.0E-4F early gate; float square root; magnitude floor at 1.0F; scale/divide and rescale both inputs; calculate float sine/cosine from current yaw multiplied by (float)(Math.PI / 180.0); then add sideways*cos - forwards*sin to X and forwards*cos + sideways*sin to Z. There is no finite-value check; NaN passes the strict comparison and propagates through the arithmetic.

The compared method bodies and direct call are identical for this slice, so none of these details changed across this adjacent patch boundary. This verdict excludes other movement kernels, input-device processing, unrelated setters, and unreviewed tick dependencies.
