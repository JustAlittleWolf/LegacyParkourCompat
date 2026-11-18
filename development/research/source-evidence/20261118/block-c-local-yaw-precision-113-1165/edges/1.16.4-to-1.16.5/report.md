# Mojmap yaw setter and first moveRelative yaw use: 1.16.4 → 1.16.5

## Scope and result

**Result: no source change in the assigned yaw operations or the ordinary dry player path.** The compared Entity.java, LivingEntity.java, and Player.java files have identical bytes across these exact sources (SHA-256 values below). This closes only the bounded operations described here; it does not establish complete 1.16.4–1.16.5 movement coverage or runtime parity.

## Exact source identity and integrity

Both trees use Mojmap names. The 1.16.4 tree has the legacy .decompile-provenance.json beside Entity.java; it pins the 1.16.4 metadata JSON, client JAR, and official client.txt mappings by input URL and SHA-256. The provenance records pipeline lpc-decompile-v1 and source hashes. The 1.16.5 ready record identifies version 1.16.5, mapping mojmap, client SHA-256 00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263, and source-manifest SHA-256 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b. Its artifact manifest pins 1.16.5/client_mappings.txt at 7931ed6d723eceb1d621d05a76e10ddf643bf468c6ecf1c4ecf377bd72cf8b8c; the manifest itself hashes to f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c.

Relevant mapped-source hashes match both provenance formats and were checked against the source files:

| Exact file | SHA-256 in 1.16.4 and 1.16.5 |
| --- | --- |
| net/minecraft/world/entity/Entity.java | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| net/minecraft/world/entity/LivingEntity.java | b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88 |
| net/minecraft/world/entity/player/Player.java | d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960 |

For 1.16.4, the exact metadata, client and mapping input pins are respectively 667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5, d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0, and 93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe.

## Compared source behavior

In both exact releases, Entity#setRot(float,float) stores yaw first as f % 360.0F, then pitch as g % 360.0F (Entity.java:326–328). There is no current-yaw addition or explicit finite-value gate in this setter. For finite yaw input the float remainder is used directly; NaN or infinity is not filtered here.

Entity#turn(double,double) is byte-identical as well (Entity.java:340–351). It computes g = d * 0.15 in double, adds that double to current float yaw, then casts the result to float; afterward it independently adds the same g to prior float yaw and casts that result to float (yRot before yRotO). Neither yaw update is clamped or guarded for finite values. The clamp calls apply only to current and prior pitch. The optional vehicle callback is outside this ordinary non-vehicle slice.

A finite witness for both operations is setRot(360.0F, 0.0F) followed by turn(1.0, 0.0) from current yaw 0.0F and prior yaw 0.0F: the setter gives current yaw 0.0F; turn then evaluates current yaw plus the finite double 0.15, casts to float, and separately evaluates prior yaw plus that same double and casts to float. This exercises the same setter and current-then-prior arithmetic in each version without relying on non-finite inputs.

The first yaw-dependent contribution to ordinary relative movement is also byte-identical (Entity.java:1075–1089). moveRelative passes this.yRot unchanged as g to getInputVector, then adds the returned vector to existing delta movement. getInputVector first returns zero only when squared input length d < 1.0E-7; otherwise it normalizes only when d > 1.0, scales by speed, computes Mth.sin(g * (float)(Math.PI / 180.0)) and Mth.cos(...), and applies those values to the horizontal input components. Those length comparisons are not finite-value checks; the method has no explicit yaw finite gate.

For a finite ordinary witness, use a yaw of 0.0F, finite nonzero forward input (0,0,1), and finite speed. The squared input length is exactly 1, so neither length gate returns early nor normalizes; the unchanged yaw reaches the unchanged radians/trigonometric expressions. This establishes the first yaw contribution only, not a downstream displacement result.

## Reachability and separate dependencies

A non-passenger player delegates its ordinary travel(Vec3) path to LivingEntity (Player.java:1387–1415); with flying ability off, the superclass path handles ordinary movement. The dry, non-fall-flying branch calls handleRelativeFrictionAndCalculateMovement after selecting ground/air friction (LivingEntity.java:1914–1927, 2022–2042), and that helper calls moveRelative (LivingEntity.java:2062–2069). The concrete scope here is a locally controlled player in this branch with ordinary finite state, no water/lava/fall-flying branch, and no vehicle.

The Mth.sin/Mth.cos implementations and the speed/friction, vector scaling/addition, collision and later travel updates are separate dependencies. They are not used here to claim a full acceleration or displacement delta; only the identical first yaw read and yaw-dependent input-vector expressions are established.
