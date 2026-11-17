# Source identity correction addendum

This immutable addendum is bound to the original wall projection report SHA-256 `BF10D3D954AFB5819881F40BB7816696ABA95C42C3BE23362881F45B14345796` and the independent audit REQUEST CHANGES verdict SHA-256 `DBDA633D92E0530A9B475DCF9BF2CDB1CA1DB2DE5147289CE722D87D75377BD9`.

The report's source-identity paragraph mistakenly labels the 1.12.2 manifest digest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` as the 1.13.2 digest. Correct endpoint identities, as verified in the exact ready markers, are:

- 1.13.2 Ornithe Feather Gen2 build 2: source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4 Ornithe Feather Gen2 build 2: source-manifest SHA-256 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

The original report's per-file source hashes were computed against canonical files and the corresponding exact endpoint manifests. This addendum corrects only the mislabeled 1.13.2 manifest identity; it does not modify the report, repeat the comparison, or expand the claim.
