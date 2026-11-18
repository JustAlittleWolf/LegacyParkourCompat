# Independent source audit: 1.19.3–1.19.4 swim-pitch vertical writer

## Bound inputs and verdict

**Verdict: ACCEPT — the bound source claims are supported.** For the ordinary active, locally controlled, nonpassenger player path, the pre-super swim-pitch Y writer, pitch-to-look Y numeric path, direct gates, head-adjacent fluid sample coordinates and block-position conversion produce no source difference between exact Minecraft 1.19.3 and 1.19.4 Mojmap sources. The inherited local-player caller and water movement branch remain reachable on both sides. No counterexample was found in the bounded claim.

This verdict is bound to both source artifacts, with exact bytes verified:

- `c-swim-pitch-vertical-1193-1194-source/report.md` — SHA-256 `8003852493e5879fa67270b8b60202f06d189ef88af12442ffee556cdf4e7a38`.
- `c-swim-pitch-vertical-1193-1194-source/addendum.md` — SHA-256 `ad79886295a9395abffe891167a10e5e4a5698e9bc71c49af709cee31c4c18bc`.

## Independent raw-source freeze

Before opening either bound artifact, I compared the raw canonical Mojmap sources in `decompiled_minecraft/1.19.3/mojmap/` and `decompiled_minecraft/1.19.4/mojmap/`. The 1.19.3 `Player.travel(Vec3)` lines 1452–1460 and 1.19.4 lines 1444–1452 both execute this operation before the superclass call:

1. Enter only under `this.isSwimming() && !this.isPassenger()`.
2. Read `this.getLookAngle().y` once as `double`.
3. Choose `0.085` iff the sampled value is `< -0.2`; otherwise choose `0.06`.
4. Apply the write iff the same value is `<= 0.0`, or `jumping`, or the still-unresolved short-circuit reaches the head-adjacent fluid test and that fluid state is nonempty.
5. Read current delta movement after those gates and set it to `delta.add(0.0, (lookY - delta.y) * scale, 0.0)`.
6. Only then enter the flying/nonflying `super.travel` branch.

The direct expression is `this.getY() + 1.0 - 0.1` on both sides. Java evaluates the call arguments left-to-right: X getter, Y getter and its two left-associative double operations `(getY() + 1.0) - 0.1`, then Z getter. The older call is `new BlockPos(double,double,double)`; the newer call is `BlockPos.containing(double,double,double)`. The old constructor delegates through `Vec3i(double,double,double)` to `Mth.floor(x)`, `Mth.floor(y)`, `Mth.floor(z)`. The newer helper directly constructs from the same three ordered floor calls. Both `Mth.floor(double)` implementations cast to `int` and subtract one iff the original double is less than the cast value. The conversion and sampled coordinates are therefore identical, including negative nonintegers and Java cast-edge behavior.

`Entity.getLookAngle()` passes the raw `getXRot()` pitch float and raw `getYRot()` yaw float to `calculateViewVector`. That method forms pitch radians as `pitch * (float)(Math.PI / 180.0)` in float arithmetic and returns Y as `-Mth.sin(pitchRadians)`. In both releases `Mth.sin(float)` indexes a 65,536-element float table using `(int)(radians * 10430.378F) & 65535`; the cast truncates toward zero before masking. Initialization fills indices 0 through 65,535 in order using `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, with the angle calculation in double until the result cast. The negative float table result is widened by `Vec3(double,double,double)` to double before `Player.travel` compares it and performs the easing arithmetic. This pitch path, index width, cast, masking, constants, table initialization and widening match.

## Reachability and inherited water movement

`LocalPlayer.aiStep()` calls `super.aiStep()`. `LivingEntity.aiStep()` reaches `this.travel(...)`; on 1.19.4 its controlling-passenger dispatch falls through to that call for an ordinary player with no controlled passenger. Dynamic dispatch enters `Player.travel`, so the pre-super writer is on the ordinary local-player tick path.

The two inherited owner gates differ textually but admit this path. In 1.19.3 `LivingEntity.travel` enters under `isEffectiveAi() || isControlledByLocalInstance()`; `LocalPlayer.isEffectiveAi()` returns true. In 1.19.4 the gate is `isControlledByLocalInstance()`; `Entity.isControlledByLocalInstance()` falls back to `isEffectiveAi()` when there is no controlling passenger, so it is true for `LocalPlayer`. The pitch block occurs before either owner gate in `super.travel`, and thus neither can suppress its sample/write. For an ordinary nonflying player, `Player.isAffectedByFluids()` returns true and inherited `canStandOnFluid` returns false. When `isInWater()` is true, the superclass water branch is therefore reached on both versions. Its body preserves the same ordered slowdown/depth-strider setup, relative movement, self move, horizontal-climbable Y handling, velocity multiplication, fluid-falling adjustment and horizontal-collision escape check. This supports the report’s inherited-water conclusion for the bounded local-player path; it does not assert equivalence of unrelated travel branches.

## Exact source and provenance identity

Both ready markers report `status=ready`, their matching exact release and `mapping=mojmap`. Their declared `mojmap.sources.sha256` digests match the on-disk manifests. Provenance records match the sidecar identities and report exact 1.19.2/1.19.3/1.19.4 batch inputs, Mojmap, Vineflower 1.12.0, Java 25.0.3+9-LTS, TinyRemapper 0.14.1 and Gradle 9.7.1. Ready and provenance sidecar SHA-256 values:

| Release | Ready marker | Provenance | Source manifest |
|---|---|---|---|
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | `59f59ab516d41eb97e09d63256ee26a68c15102052bf7acafdbd5440b72547d0` | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |

The 1.19.4 source-manifest digest in the table is `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` (as declared by the ready marker and verified on disk).

Every relied-on source digest below was recomputed from the canonical source bytes and matched the corresponding line in that version's verified manifest:

| Source | 1.19.3 SHA-256 | 1.19.4 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` |
| `net/minecraft/world/entity/Entity.java` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |
| `net/minecraft/util/Mth.java` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` |
| `net/minecraft/core/BlockPos.java` | `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a` | `6f7d806a3a3ca3204db248a7d0dba4908685224dc5a3350e2eb51f69f97d9715` |
| `net/minecraft/core/Vec3i.java` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` | `36db6e4b3001c2ea4e5c218ba601d47b76a91e60455f9088b1db4893cef623c3` |
| `net/minecraft/world/entity/LivingEntity.java` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` | `c8d91af61f87aaa1666de79696d7cd9d4a8212d05f873bdaf28d7bb2926bf165` |
| `net/minecraft/client/player/LocalPlayer.java` | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` |

## Audit boundary

This is source acceptance for the bound claim and these exact two source publications. It does not establish the precise release where any behavior was introduced, full-pair tick coverage, implementation status, or runtime parity.