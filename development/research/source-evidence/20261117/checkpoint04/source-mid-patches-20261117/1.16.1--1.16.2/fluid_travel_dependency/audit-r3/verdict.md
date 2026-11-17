# Independent targeted audit: 1.16.1→1.16.2 fluid report r3

Verdict: **ACCEPT — the r3 correction accurately narrows the unchanged-method statement and accounts for the changed `LocalPlayer.aiStep()` method.** This verdict is bound to report SHA-256 `80D66EDB973BA4C102E00200EFFB5A3D901800894A9983A1EECBB7454D382BD1`.

The only r2-to-r3 change corrects the overbroad claim identified in the r2 audit (`CF4D3EFDD4F8E5F0A8B66CCE747EAAFAFC2ED0931AAB29BD9C3CA42FCD13F09B`). The r3 text now limits unchanged methods to `Player.tick()`, `Player.aiStep()`, `Player.travel(Vec3)`, and `LocalPlayer.tick()`. It explicitly states that `LocalPlayer.aiStep()` changes from `checkInBlock(...)` to `moveTowardsClosestSpace(...)`, while preserving the `super.aiStep()` call in both releases. This matches the prior audit's paired source check.

The cross-reference to `local-player-suffocation-escape.md` is supported by the accepted separate witness packet: report SHA-256 `7DE358A9A00D94485900903310BED28B40B0EE3FACB08BBD47A0167AB436D09A`, verdict SHA-256 `91CE8B95ECC4E422BB37648EE89D75F2996F462CD3F4F5F70150E4880783F902`. The cited candidate file hash is `868DF34DD179E59546793F38DCE241395C8B8B3A3CA676A51EB012C2E56866B5`.

Reused unchanged from the r2 audit: exact endpoint identities and source bindings; the fluid contact/tracking comparison; and the `LocalPlayer.tick()`/`Player.tick()`/`Player.aiStep()`/`Player.travel(Vec3)` dispatch and method-body checks. The r3 report hash was independently recomputed from the immutable bytes; no canonical source or source tree was rehashed.

Acceptance is limited to this r3 correction and the previously audited fluid dependency slice. A concrete separating water/lava state witness and full pair coverage remain open. The separately audited water/lava resource packet is distinct from this report. No runtime or pair-freeze conclusion is made.