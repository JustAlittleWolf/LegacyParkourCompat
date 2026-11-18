# Mouse-sensitivity identity correction

## Binding and disposition

This sidecar binds to the frozen source report SHA-256 `f94d39bf82443713e9ae0fdbb6f9b835aea0a38d536f0a19012d5eadb6546525` and the signed independent audit `c-turn-precision-1165-117-motion-audit/verdict.md`, whose SHA-256 was verified as `9882f4b790f6183945cfd1cd96c9670d02fd58548e76c5969de71e49db7d318f`. The original source report remains byte-for-byte unchanged.

The original report's `sensitivity=0.5`, `+4` mouse-pixel callback does **not** produce `turn(0, 4)` in either release. The source-derived pitch result `e=4.0` remains a valid direct `Entity.turn` argument calculation, but it is not the caller result for that stated callback setup. Therefore that particular callback-to-movement witness is withdrawn.

## Exact correction at sensitivity 0.5

In both pinned Mojmap releases, `Options.sensitivity` has type `double` (`Options.java:49` in 1.16.5; `:64` in 1.17). `MouseHandler.turnPlayer` evaluates `sensitivity * 0.6F + 0.2F` into a `double` (`MouseHandler.java:217` and `:213`). Java widens each float literal to double for these operations. At the stated sensitivity:

```text
f = 0.5 * widen(0.6F) + widen(0.2F)
  = 0.5000000149011612
mouse scale = f * f * f * 8.0
            = 1.0000000894069698
e = accumulatedDY * mouse scale
  = 4.0 * 1.0000000894069698
  = 4.000000357627879
```

The selected nonsmooth, noninverted, nonscoped branch uses this same scale in both releases: 1.16.5 calculates `g=f*f*f*8.0`; 1.17 calculates `g=f*f*f` then `h=g*8.0`. Starting from the frozen report's `xRot=1.4599311351776123F`, the exact caller value gives:

| `Entity.turn` result | 1.16.5 | 1.17 |
|---|---:|---:|
| pitch increment used | double `0.6000000536441819` | float `0.6000000834465027F` |
| resulting current pitch | `2.0599312782287598F` (`0x4003d5ea`) | `2.0599312782287598F` (`0x4003d5ea`) |

Thus the next Elytra path reads identical current pitch for this corrected callback. The earlier different `move` vectors for direct `e=4.0` do not follow from the claimed four-pixel callback at sensitivity 0.5.

## Confirmed lawful sensitivity-1 witness

The signed audit's replacement witness is source-reachable: `Option.SENSITIVITY` allows the inclusive range `[0.0, 1.0]` (`Option.java:234–241` and `:239–246`), so sensitivity `1.0` is legal. Initialize the cursor baseline first, then use a `+1.0` Y callback with mouse grabbed, window active, smooth camera off and invert-Y off. Keep the 1.17 scoped-camera special branch inactive. The actual shared callback calculation is:

```text
f = 1.0 * widen(0.6F) + widen(0.2F) = 0.8000000268220901
mouse scale = f * f * f * 8.0 = 4.096000411987318
e = accumulatedDY * mouse scale = 1.0 * 4.096000411987318
  = 4.096000411987318
```

For the audit's `xRot=-4.0696001052856445F`, 1.16.5 uses the double increment `0.6144000617980977` and rounds current pitch to `-3.455199956893921F`. In 1.17, the float increment is `0.6144000291824341F` and current pitch becomes `-3.4552001953125F`. Both are finite, strictly in range, and avoid the pitch clamp.

I independently evaluated the next dry active Elytra travel from the audit's state: nonpassenger local player, `isFallFlying()=true`, `onGround=false`, `isSwimming()=false`, abilities flight off, no water/lava/collision, yaw zero, no movement keys, and delta movement `(0.1,-0.1,0.0)`. `isSwimming=false` bypasses `Player.travel`'s pre-super swimming adjustment; abilities flight off takes its ordinary `super.travel` path. The local player's effective-AI gate permits `LivingEntity.travel`. The resulting pitch sine lookup products truncate to indices `64908` and `64907`; the cosine lookup is index `15755` on both sides. The source-order flight calculation yields the following post-drag `move(SELF, getDeltaMovement())` arguments:

| Release | Requested `(x, y, z)` |
|---|---|
| 1.16.5 | `(0.08910000085830688, -0.10532021111523943, 0.020358057324752363)` |
| 1.17 | `(0.08910000085830688, -0.10531901066717987, 0.020357716253575527)` |

These match the signed audit's numeric witness. In the stated collision-free location, the request is also the displacement. This confirms propagation for the sensitivity-1 callback witness, not the sensitivity-0.5 callback previously stated.

## Source identity

Both sides are the exact pinned `1.16.5/mojmap` and `1.17/mojmap` outputs named in the bound audit. The verified source pins used for the sensitivity correction match its table: 1.16.5 `Options.java` SHA-256 `431ed10af0ee5d083a007a18208c6b117541c4cd4e4d37ad7ddd1a591de61190`, 1.17 `Options.java` `30b91d23563e5b6ffd903e1f6e0dccc39352eb13dbe5e1aaa6d3a0e36118706e`; 1.16.5 `MouseHandler.java` `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949`, 1.17 `MouseHandler.java` `6ff70f8634171709ba08a42158aea7c8d4d3974d73f89b43bfe08a5ca6db5fb2`. No other report was read, and no product, runtime, Git, network, build, or test action was performed.
