# Independent source review: F-3 Nether lava shallow-overlap correction

- Decision: **ACCEPT** the exact corrected snapshot identified below.
- Review date: 2026-10-09.
- Scope: the F-3 shallow-overlap Nether LAVA Player case, its corrected exact-coordinate witness, direct source dependencies, and reachability gates.
- Pair status: `1.21.11--26.1.2` remains partial. This finding acceptance does not freeze or complete the pair.
- Runtime validation: not performed.

## Immutable identities and review event

I independently read the candidate bytes with `git show` at commit `b2b493a2521b036bca2fa590feeda3194c49bd4c`:

- Path: `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-3-nether-lava-shallow-overlap-r2-2026-10-09.md`
- Git blob: `73a244f66a6501b1d48f11fc4bcbde95d25c8518`
- Raw SHA-256: `69146a6130e72d7fffdf4bfa71954c41c62dc57aebfa0246f040e079c5f28907`

The assignment's correction-binding commit is `bd244527013e8eb7a5ae912c4fe6ce42aba5dbcc` (parent `b2b493a2521b036bca2fa590feeda3194c49bd4c`). Its pair-run event records this exact path, blob and SHA-256, names the earlier review's `REQUEST CHANGES`, and preserves the original snapshot/event. This decision accepts only the R2 bytes; it does not rewrite or supersede that historical event.

## Publication and source integrity

The canonical exact ready roots are `ready/1.21.11/mojmap` and `ready/26.1.2/unobfuscated`. I verified their live markers' exact version IDs, namespaces and ready status, and recomputed the marker and both manifests. I independently recomputed the candidate-cited Java source hashes and matched each file to its version's source manifest. The exact client jars were identified from the artifact manifests; their `data/minecraft/tags/fluid/lava.json` bytes match each other at SHA-256 `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`.

| Side | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Client jar SHA-256 |
|---|---|---|---|---|
| A: 1.21.11 Mojmap | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| B: 26.1.2 unobfuscated | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

Reviewed Java source hashes (all matched their source-manifest entries):

- A: `Entity.java` `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; `Player.java` `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`; `LavaFluid.java` `8fabbb9a75d7620963f7a44141280bb08e0363ece7a425fcd37334e422af5abb`; `DimensionTypes.java` `532319fc7694da6007767e3ce521e5e7ea3a7926c1e782eba132caf03cd202d3`; `EnvironmentAttributes.java` `a35798b671dc8c2467c0b3278a697af846b184567b8493fc0d9c4141085e2a46`; `FlowingFluid.java` `905e5a02ab10b23b5f6e64a0f732ee552ae8895801e1944d9850a41cc8bce91a`; `FluidState.java` `146a5e6a19a93da631a6f4ed57020791bde31087fcedc6d52c68b1c582b71b0f`; `Vec3.java` `a4050765738a0cfdb1100b83f2ba4effd40155de99bb703a8381dbe775148ec1`; `FluidTagsProvider.java` `ab9fa0839166ea355bd34ca6c3f789415ee5dddc93779d97c2d6f6d0a931d2b7`; `EntityType.java` `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e`; `EntityDimensions.java` `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4`; `Blocks.java` `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23`; `BlockBehaviour.java` `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17`.
- B: `Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `EntityFluidInteraction.java` `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`; `Player.java` `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`; `LavaFluid.java` `423ff44a57c316f58c21a66903a4ef2c1b9c3d26316bdc4d301727575f93703f`; `DimensionTypes.java` `f434df28f947b552c5c54cc96004521f72d572f7a6a3493a9ace20e70efc3576`; `EnvironmentAttributes.java` `cc5147e19bba69196a18a554be7d780ec248ad4d0e9ff46553e2247cc5da13f6`; `FlowingFluid.java` `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28`; `FluidState.java` `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18`; `Vec3.java` `57b08ae818868a4fffdc9b5dadba2b127138c8945c2194f6f4b01e6150cd3bcb`; `FluidTagsProvider.java` `3e3fab74c346256ba325b8953f4d6a6d8cf0670b182652c111053b51de8bec05`; `EntityType.java` `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229`; `EntityDimensions.java` `27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a`; `Blocks.java` `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`; `BlockBehaviour.java` `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`.

## Corrected exact-coordinate witness

The correction is internally consistent. It uses one double-precision position for both sides:

```java
float h = 1.0F / 9.0F;
double topB = 64.0D + (double) h;
double playerY = topB - 0.003D;
```

The A and B sources then apply their own distinct fluid-top expressions to that same player box. This fixes the exact mismatch identified by the original review; acceptance is based on recomputed values, not merely the fact that both previous formulas took the desired branches.

The player stands at `(100.5D, playerY, 100.5D)` with zero horizontal delta movement, no vehicle and `abilities.flying == false`. Exact `EntityType.PLAYER` dimensions are `.sized(0.6F, 1.8F)`; `Entity.setPos(x,y,z)` builds the box from those dimensions and position, with Y as the box minimum. Standing pose and scale 1 keep that box. After the source's `.deflate(0.001)` fluid-query margin, X/Z scan only block 100 and Y scans blocks 64 and 65; only block `(100,64,100)` contains tagged lava. The amount-2 neighbor at X=101 and source at X=107 are outside the player scan.

The exact float and double results are:

- `h` as a float: `0.1111111119389534`.
- `topB = 64.0D + (double)h`: `64.11111111193895`.
- `playerY = topB - 0.003D`: `64.10811111193895`.
- A's deflated box minimum: `playerY + 0.001D = 64.10911111193896`.
- A's source computes `blockY + fluidHeight` with `blockY` an `int` and `fluidHeight` a `float`, so `64 + h` rounds as float before widening to double: `topA = 64.11111450195312`.
- A overlap: `topA - (playerY + 0.001D) = 0.002003390014166939`.
- B's `fluidBottom` is a `double`; `fluidBottom + fluidState.getHeight(...)` therefore yields `topB` in double precision. B measures tracker height from the raw entity box minimum: `topB - playerY = 0.0030000000000001137`.

A's single-cell `getFlow()` is westward `(-1,0,0)`. Its Player branch scales that vector by the measured overlap (because it is below `0.4`) and then by Nether strength `0.007`, yielding westward magnitude `0.002003390014166939 * 0.007 = 1.4023730099168574E-5`. That exceeds `Vec3.normalize()`'s strict `1.0E-5F` floor. With old X/Z velocity zero, it remains below the minimum-impulse comparison `0.0045000000000000005`; A normalizes it and writes the westward minimum impulse `(-0.0045000000000000005, 0, 0)` through `setDeltaMovement`.

B accumulates the same one-cell westward unit flow scaled by its tracker height, so its accumulated vector is `(-0.0030000000000001137,0,0)` and its squared length is `9.000000000000683E-6`. Java's `1.0E-5F` cutoff is `9.999999747378752E-6`; the strict B condition is true. `Tracker.applyCurrentTo()` returns before averaging, multiplying by `0.007`, checking the minimum impulse, or adding movement. The corrected witness therefore proves different source operations for the same exact initial coordinate and state.

## Enclosing gates and reachable fluid chain

The direct dependency closure for this bounded witness is supported on both exact sides:

1. **Tick and Player gate.** A's `Entity.tick()` reaches `updateInWaterStateAndDoFluidPushing()`; the method applies LAVA at `0.007` when `FAST_LAVA` is true, then calls `updateFluidHeightAndDoFluidPushing()` (`Entity.java:501–505, 1501–1506, 3509–3573`). B's tick reaches `updateFluidInteraction()` (`Entity.java:522–525`), whose lava branch applies the same Nether scale (`1558–1578`). A and B `Player.isPushedByFluid()` return `!abilities.flying`; the selected non-flying Player is pushed by fluid. B's `Entity` constructs trackers for both `FluidTags.WATER` and `FluidTags.LAVA`, and `update()` receives `ignoreCurrent == false` for this Player.
2. **Loaded-region and box gates.** A returns early only if `touchingUnloadedChunk()` is true; its query inflates the Player box by one block and calls `hasChunksAt` (`Entity.java:3510–3512, 3576–3582`). All resulting X/Z coordinates remain in the fully loaded chunk containing block 100. B's fluid interaction box is the Player box deflated by `0.001`; no vehicle means the vehicle modifier is skipped (`Entity.java:4015–4023`). B scans cells only after `hasFluidAndLoaded()` succeeds on its horizontal one-block margin and sections (`EntityFluidInteraction.java:32–43, 91–119`). The chosen section is loaded and contains fluid. Thus neither enclosing load check blocks the one-cell scan.
3. **LAVA tag and Nether configuration.** The exact client-jar `data/minecraft/tags/fluid/lava.json` on both sides contains `minecraft:lava` and `minecraft:flowing_lava`; its bytes have the hash above, and paired `FluidTagsProvider` sources identify the same two members. The paired Nether `DimensionTypes` set `EnvironmentAttributes.FAST_LAVA` to true (A line 81; B line 91); the attribute's generic default false does not override that Nether value. Both `LavaFluid.getDropOff()` return 1 in this condition (A lines 164–166; B lines 165–167), and both entity paths use strength `0.007`.
4. **Reachable level states and current.** In both sources, `FlowingFluid.getNewLiquid()` takes the highest same-type horizontal neighbor amount minus the lava drop-off and returns a non-falling flowing state for positive amounts (A lines 160–195; B lines 161–196). Therefore source amount 8 at X=107 yields amount 7 at X=106 and then 6 down to amount 1 at X=100 along the supported channel. `getOwnHeight()` is `amount / 9.0F`; `FluidState.getHeight()` delegates to `FlowingFluid.getHeight()`, which returns own height when no same fluid is above. The target's only nonzero horizontal `getFlow()` term is east's `1.0F/9.0F - 2.0F/9.0F`, so the normalized result points west. The north, south and west empty-fluid neighbors are bounded by STONE: `Blocks.STONE` uses `Properties.of()`, the default state is solid, and `blocksMotion()` is true. `getFlow()` therefore does not take its empty-neighbor-below fallback on those sides. The empty block above target keeps the level non-falling and does not change its top to 1.0F.

These are exact-source reachability and state-construction checks, not a runtime simulation. The resulting state is a vanilla supported channel in a fully loaded region; no caller-supplied flow vector is assumed.

## Limits

Acceptance is limited to this corrected, non-mounted standing Player Nether LAVA shallow-overlap witness and the source-level operation difference it demonstrates. The first changed release remains unknown within `(1.21.11, 26.1.2]`. Other lava heights/vectors, mounted fluid boxes, eye/contact consumers, broader water/lava source closure and remaining S4.6 work are not closed here. The pair remains partial. No mod implementation, wiki lane, build, test, decompilation, client/server, TAS, Gym, Docker or runtime activity was used.
