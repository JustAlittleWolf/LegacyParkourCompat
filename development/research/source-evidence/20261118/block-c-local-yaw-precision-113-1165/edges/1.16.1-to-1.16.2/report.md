# 1.16.1 → 1.16.2: ordinary local yaw turn and dry relative acceleration

## Exact source identity and integrity

Both source trees are exact-run `decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G` output, published from staging run `mojmap-1.16.1-to-1.16.2-e70b9be363894d4898ba9ec4c224103a`. The exact-version metadata IDs are 1.16.1 and 1.16.2. Mapping family is Mojmap; each version's pinned inputs include its `client_mappings.txt` and remapped `client-mojmap.jar` (input hashes below). Decompiler provenance records Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Java 25.0.3+9-LTS, Gradle 9.7.1.

- **1.16.1:** `decompiled_minecraft/1.16.1/mojmap.provenance.json` SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; ready marker SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`. The ready marker's source-manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` matches the actual `mojmap.sources.sha256`; its artifact-manifest SHA-256 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` also matches. Input hashes: client mappings `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`; Mojmap jar `f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d`; client jar `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.
- **1.16.2:** `decompiled_minecraft/1.16.2/mojmap.provenance.json` SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; ready marker SHA-256 `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`.

The 1.16.2 marker records source-manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` and artifact-manifest SHA-256 `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`; both match the corresponding files. Input hashes: client mappings `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`; Mojmap jar `559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90`; client jar `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.

For each source below, the SHA-256 computed from the published source file matches its entry in that version's `mojmap.sources.sha256` (`<hash> *<relative path>`):

| Source | 1.16.1 SHA-256 | 1.16.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |

## Comparison

**No yaw-turn or yaw-relative-acceleration difference was found between these releases in the assigned scope.** The paired method bodies for `Entity.turn`, `Entity.moveRelative`, `Entity.getInputVector`, `LivingEntity.travel`, `LivingEntity.handleRelativeFrictionAndCalculateMovement`, and `Player.travel` are text-identical across the two exact Mojmap source trees. This is a source comparison only; no numeric divergence witness applies.

`Entity.turn(double d, double e)` is the same in both releases (`Entity.java:332–344` in 1.16.1; `:340–352` in 1.16.2). It computes yaw increment `g = d * 0.15` in `double`; then stores current yaw `(float)(yRot + g)`, followed later by prior yaw `(float)(yRotO + g)`. Each addition happens before its `float` cast. Yaw is not wrapped, modulo-normalized, clamped, or checked with `Double.isFinite` in this turn path. The pitch assignments and pitch-only clamp are adjacent but outside this yaw finding. `setRot`'s `% 360.0F` is a separate setter and is not used by `turn`.

The active player route is present in both versions: `LocalPlayer.aiStep` calls `super.aiStep` (1.16.1 `LocalPlayer.java:813`; 1.16.2 `:788`); `LivingEntity.aiStep` passes `(xxa, yya, zza)` to `travel` (1.16.1 `LivingEntity.java:2457`; 1.16.2 `:2459`); and `Player.travel` delegates to `super.travel` in its ordinary non-flight path (1.16.1 `Player.java:1380–1410`; 1.16.2 `:1387–1417`). Hardware input timing is not examined.

Within `LivingEntity.travel`, the dry/non-fall-flying branch calculates `u = onGround ? t * 0.91F : 0.91F`, then calls `handleRelativeFrictionAndCalculateMovement`; this branch runs for either grounded or airborne movement after water, lava and fall-flying branches are excluded (1.16.1 `LivingEntity.java:1912–2044`; 1.16.2 `:1914–2046`). That helper applies `moveRelative(getFrictionInfluencedSpeed(f), vec3)`. `Entity.moveRelative` adds `getInputVector(vec3, f, yRot)` to delta movement. `getInputVector` retains the same `double` squared-length gate and normalization, `float` sine/cosine after `g * (float)(PI / 180.0)`, and rotation expression in both versions (`Entity.java:1073–1088` / `:1075–1090`). Therefore a real dry grounded/air controlled player accelerates in the same direction from current yaw in both versions; ground/air changes friction through `u`, not the yaw transform. The prior-yaw update order in `turn` is likewise unchanged.

