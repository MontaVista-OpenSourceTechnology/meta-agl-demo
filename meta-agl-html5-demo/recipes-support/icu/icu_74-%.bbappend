<<<<<<< HEAD:meta-agl-html5-demo/recipes-support/icu/icu_74-%.bbappend
require ${@bb.utils.contains('AGL_FEATURES', 'agldemo', 'icu_74-1_agldemo.inc', '', d)}
=======
require ${@bb.utils.contains('AGL_FEATURES', 'agldemo', 'icu_agldemo.inc', '', d)}
>>>>>>> fe2024e (Update bbappends for upstream version bumps):recipes-support/icu/icu_74-%.bbappend
