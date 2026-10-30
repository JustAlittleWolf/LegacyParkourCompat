# Entity push witness sign correction

The corrected finding draft `entity-push-impulse-precision-corrected.md` and the local route correction in `entity-push-impulse-precision-erratum.md` used the valid Mob-as-caller route, but reversed the local impulse sign. Preserve those files unchanged and use this correction for the conditional witness.

Let receiver = local player at x=+0.03 and argument = Mob at x=0 (z equal). `Entity.push(Entity)` computes `d = Mob.x - player.x = -0.03`. The local player is the receiver `this`, so its write is `this.push(-d, 0, -e)`, yielding positive X. With `pushthrough=0`, the source-order terms are `+0.008660254322328477` in 1.16.5 and `+0.008660254166892228` in 1.17.1 (difference `-1.5543624894143182e-10`). This is the independent audit’s corrected conditional arithmetic result, not a runtime trace.

The independent source audit is `.native-source/20261030/1165-1171-pool38/jump_push_independent_audit/audit.md` SHA-256 `d38df127fcda4f7aba31206afc787b5079b1016bacf74ce6d9331a38ce82f03c`; its route erratum is `erratum.md` SHA-256 `e19721ff653c5a62ba118f98036cf4cae9f8bbf5711ee7b9fa9a8954f4911797`. The independent audit confirms the Mob caller route, rejects LocalPlayer-as-caller and RemotePlayer paths, and accepts the endpoint source operation under explicit gates.
