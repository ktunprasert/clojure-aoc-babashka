# https://just.systems

default:
    bb -m core 1e.txt


build:
    clojure -X:uberjar
    # native-image \
    #   --no-fallback \
    #   --initialize-at-build-time \
    #   --report-unsupported-elements-at-runtime \
    #   -H:+ReportExceptionStackTraces \
    #   -jar aoc.jar \
    #   aoc-solver

bench:
    hyperfine 'java \
        -server \
        -XX:+UseG1GC \
        -XX:MaxGCPauseMillis=50 \
        -XX:+TieredCompilation \
        -Dclojure.compiler.direct-linking=true \
        -jar aoc.jar day1-large.txt'
