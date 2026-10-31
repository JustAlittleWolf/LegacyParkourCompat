# Renewal — exact-boundary bed-wake finding

Reviewed finding SHA-256: `508f35100e17eadb6a0d94b688d899b9012c01f08bf20816af680f0fc885d4bc`
Boundary report SHA-256: `1bfa29de90e99fbb1a82370035d8f7edad2d5f85162458f0d5a6607de137376`
Accepted concrete witness SHA-256: `d0fdc5ea63ecf1b2cb9f637255280c7873a4971501104b2096302175927e998e`

## Verdict

**ACCEPT** this boundary-specific canonical finding, bound to the immutable finding revision above and the cited boundary report and accepted witness.

The corrected finding hashes to the requested revision. Its 1.16.1 summary now includes both `noCollision` and the downward-expanded-AABB dangerous-block rejection; the specified air/stone witness passes both. The 1.16.2 summary includes the yaw ordering, helper floor selection, dangerous-block gates and final candidate collision acceptance, all of which pass for the stated world. The old/new source identities and method groups match the boundary report; checked source hashes for 1.16.1 and 1.16.2 retain the documented old/new forms, with the .2 form continuing unchanged through .5.

The client action-2 route to `Player.stopSleepInBed(false,false)` and `LivingEntity.stopSleeping` position write is present in the boundary releases and consistent with the accepted endpoint route review. Old center `(-0.5,64.0000002,-0.5)` and new west/east centers `(-0.5,64,0.5)` / `(1.5,64,0.5)` are correct; collision clearances and the `2.0E-7` Y difference match the concrete witness. Bed-entry eligibility remains excluded.

No other issue found. No product, wiki, backlog, build, test, runtime, or decompile action was performed.
