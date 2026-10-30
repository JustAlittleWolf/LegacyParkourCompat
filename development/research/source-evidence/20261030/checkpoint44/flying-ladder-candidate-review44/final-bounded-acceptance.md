# Final bounded acceptance: flying ladder suppression

**Verdict: ACCEPT** for the bounded V1_15 profile alias (1.15 and 1.15.1) and V1_15_2 (1.15.2), based on the source comparison through 1.16.0. This does not accept historical behavior for profiles before V1_15, nor does it establish runtime parity.

Reviewed product commits:

- Implementation `7c3a75943c4a319c726ffd8f082d9e96c351ec46`.
- Correction `73aeaa688b192b4e145575b523df1920ff6f2c8e`, which adds only the missing `ModifyReturnValue` import identified in the preceding review. That removes the compile blocker; no other implementation changes were introduced.

Evidence reports:

- Bounded 1.15/1.15.1 identity and source addendum: `alias-1.15-source-addendum.md`, SHA-256 `2946BA552B57A11C9D79B62A47D7D402C70B967D3C91004B95E44E293B7FEBA9`.
- Product review (before import-only correction): `final-product-review.md`, SHA-256 `93B4EF7F9AC54753A351FB5BA91971F2DA18B1A5D693BCE6BB8EF12956F15C6B`.

Exact Mojmap source confirms the inherited shift-only predicate in 1.15, 1.15.1 and 1.15.2; the 1.16.0 LocalPlayer override adds the not-flying gate, and the accepted boundary report confirms its persistence through 1.16.5. Thus `LadderSlideSuppression` at V1_15_2 supplies the old behavior for selected V1_15 and V1_15_2 profiles. Under the resolver's smallest eligible `emulates` rule, the V1_14_2 native-return change acts as a bounded implementation barrier for V1_14_2 and earlier profiles. It is not a source claim that native behavior equals historical behavior there.

The final hook changes only `LocalPlayer.isSuppressingSlidingDownLadder()Z`'s return value. The exact current 26.2 compiled LocalPlayer declares that `()Z` method; its superclass implementation feeds the predicate used by the current LivingEntity climb helper. `LocalPlayerMixin` is already listed in the existing environment-client mixin config. Its `MovementRuntime.find` call preserves client/player/enabled-profile guards and native fallback. Annotated public no-arg changes are discoverable automatically. Vanilla retains the clamp, scaffolding exclusion, Player condition and movement order.

The review is static. No build, tests or game launch were run; the root is handling the build. This acceptance does not prove runtime parity.
