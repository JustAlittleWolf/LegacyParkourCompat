# Independent audit: 1.21.11 → 26.1.2 fall-flying source slice

Verdict: `ACCEPT` the bounded `NO_DIFF` finding.

Audited report: `build/later-source-20261116/1.21.11--26.1.2/fall_flying/coverage.md`
Audited report SHA-256: `28624A6834426FAA8EE73F714D715182D2F96DAC3E784D7A45AF4A50067B52FA`

## Source identity and evidence binding

I independently read the endpoint ready markers, provenance records, source manifests, and exact canonical Java files. The source and artifact manifest digests in the report match the endpoint ready markers; the provenance records identify the stated release and namespace. I recomputed each cited Java-file SHA-256 and confirmed every cited anchor against those bytes.

| Endpoint | Canonical source root | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|---|---|---|
| 1.21.11 Mojmap | `decompiled_minecraft/1.21.11/mojmap` | `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B` | `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFc179D494E5986F78FE29F72` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 26.1.2 unobfuscated | `decompiled_minecraft/26.1.2/unobfuscated` | `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7` | `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

The cited file hashes were independently recomputed:

- `LocalPlayer.java`: 1.21.11 `948E94F8E874B2F72E9A689E6E3BA1933F5728EC381D64F3BB5E2388718BD477`; 26.1.2 `433FD995AD317AF0F6EF0E50C1E8E3483CB8F00E0E327D4EDF27A4DD99666EBE`.
- `Player.java`: 1.21.11 `8E97167350A91741D0AA10D3B0D92A33150ED6DCCDC94CD5B37D9C7CA22BCC81`; 26.1.2 `44CF28E0C64E78D39FD13368E9991381DBEBAB67029070CB9DDC43F09D45D14D`.
- `LivingEntity.java`: 1.21.11 `19ED2D565858C401C69A06750B054A633A20EA864CAB3A753B5C767F0BDD60E8`; 26.1.2 `C3B64DE8DBABA8AD8A7CCD4F33255346D8206E66F12CA91260971BF5E5AC93BD`.

## Audit result

- **Local start trigger:** At both cited `LocalPlayer.aiStep` anchors, a held jump plus the same three negative gates (creative-flight toggle edge, prior jump edge, and climbable state) calls `tryToStartFallFlying()`. Success sends the same start-flying command; the method then samples `isFallFlying()`. Names and formatting differ, with no change to gate or operation order.
- **Player eligibility and flag write:** Both `Player.canGlide()` overrides reject creative flight before delegating. Both start methods require `!isFallFlying() && canGlide() && !isInWater()`, then set shared flag 7 and return true; failure returns false.
- **Equipment eligibility and query:** Both `LivingEntity.canGlide()` methods first require not grounded, not a passenger, and no levitation; then scan `EquipmentSlot.VALUES` in order and accept the first slot passing `canGlideUsing`. Both helpers require the GLIDER component, a non-null EQUIPPABLE component whose slot matches the scanned slot, and `!nextDamageWillBreak()`. `isFallFlying()` reads shared flag 7 at both endpoints.
- **Travel dispatch and response:** In both `LivingEntity.travel` methods, fluid handling precedes the fall-flying flag check, which precedes air travel. Within `travelFallFlying`, both route climbable state through ordinary air travel and `stopFallFlying`; otherwise both snapshot current delta movement and its horizontal distance, calculate and write the updated fall-flying vector, move with `MoverType.SELF`, then call the collision handler only off the client side.
- **Vector arithmetic:** Comparing each operation in sequence confirms the same look-angle horizontal magnitude, float-cast degree-to-radian pitch, starting horizontal movement length, effective gravity, squared-cosine lift, gravity update, negative-Y correction, negative-pitch dive, 0.1 horizontal alignment, and final `.99F, .98F, .99F` multiplication. The collision handler retains the same `horizontalCollision` gate, difference, `(float)(diff * 10.0 - 3.0)` conversion, and `> 0.0F` threshold. Its sound/damage effects are outside the accepted movement claim.
- **Stop behavior:** Both `stopFallFlying()` methods set shared flag 7 true and then false. No operation or ordering difference was found in this bounded path.

## Limits

This audit accepts only the stated endpoint slice. The report expressly disclaims intervening releases and pair closure; it makes no resource-content or concrete glider-item claim. It excludes packet serialization, server durability/event behavior and collision damage effects, fluid response, ordinary ground/air input implementation, the separately assigned flying-climbable predicate, vehicles, and visual/sound callbacks. I found no omitted changed path within the report's declared fall-flying start/stop and ordinary local-player travel boundary. This verdict does not establish an exact change cutoff, complete pair coverage, implementation requirements, or runtime parity.
