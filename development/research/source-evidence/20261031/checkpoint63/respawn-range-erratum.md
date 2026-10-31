# Erratum: respawn jump-strength profile range

The product review at `review.md` remains immutable; its **ACCEPT** verdict is unchanged.

The sentence stating that the 1.20.5 implementation supplies the reset for “profiles selecting 1.20.5 or later” was too broad. `ChangeResolver` selects the closest registered change whose `emulates` version is not older than the selected profile. The 1.20.5 reset therefore applies only to the 1.20.5/1.20.6 old-version group. Starting with the 1.21 group, no newer hook change is registered, so the hook is absent and native respawn behavior is retained. The 1.20.2 empty-result anchor continues to preserve the prior partial behavior below 1.20.5; it does not assert historical equality for those older jump-power paths.

Reviewed identities: product integration `71245cd3a49106252cf98423049c25fde6f1ed07`, base `6e9780398a296d466d5dcc97e1b84fb65585f269`, candidate `d2bdc1976c259f24b084dcd793d2c29a21589780`.
