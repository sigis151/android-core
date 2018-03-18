node {
    stage 'setup'
    def mainBranch = "develop"
    def mainBuildFlavor = "Debug"
    def mainBuildType = ""
    def mainBuildVariant = mainBuildFlavor + mainBuildType
    def buildVariants = ["Debug"]
    def modules = ["blaster-common"]
    checkout scm
    properties([buildDiscarder(logRotator(numToKeepStr: '50'))])
    def revisionHash = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
    def revCount = sh(script: "git rev-list $revisionHash..origin/$mainBranch --count", returnStdout: true)
            .trim().toInteger()
    if (revCount > 0) {
        echo "branch is behind dev, marking build as UNSTABLE"
        currentBuild.result = "UNSTABLE"
    }
    sh "chmod +x ./gradlew"
    gradleNoDaemon('clean')

    stage 'test'
    gradleNoDaemon("test${mainBuildVariant}")
    step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/**/TEST-*.xml'])

    stage 'assemble'
    def assembleCommand = ""
    for (String variant : buildVariants) {
        assembleCommand += "assemble$variant "
    }
    gradleNoDaemon(assembleCommand)
    archive '**/build/outputs/aar/*.aar'

    stage 'analysis'
    def clocDirs = ""
    for (String module : modules) {
        gradleNoDaemon(":" + module + ":lint${mainBuildVariant}" + " :" + module + ":findbugsXml")
        clocDirs += "$module/src "
    }
    sh "cloc --by-file --xml --out=app/build/reports/cloc.xml $clocDirs"

    stage 'publish analysis'
    step([$class: 'LintPublisher', pattern: '**/lint-results*.xml'])
    step([$class: 'FindBugsPublisher', pattern: '**/build/reports/findbugs/*.xml'])
    def variantClassPath = mainBuildFlavor.substring(0, 1).toLowerCase() + mainBuildFlavor.substring(1)
    step([$class          : 'JacocoPublisher', classPattern: "**/build/intermediates/classes/${variantClassPath}/${mainBuildFlavor.toLowerCase()}",
          exclusionPattern: '**/R.class, **/R$*.class, **/BuildConfig.*, **/Manifest*.*, '
                  // Misc library specific
                  + '**/*$ViewInjector*.*, **/*_*, **/Dagger*, '
                  + 'io/realm/**, **/com/viewpagerindicator/*, **/*$ViewBinder*.*, '
                  // Android specific
                  + '**/*LinearLayout.*, **/*FrameLayout.*, **/*RelativeLayout.*, **/*ImageView.*, '
                  + '**/*ConstraintLayout.*, **/*GridLayout.*, **/*TableLayout.*, **/*ListView.*, '
                  + '**/*ScrollView.*, **/*WebView.*, **/*SearchView.*, **/*VideoView.*, **/*SurfaceView.*, '
                  + '**/*NestedScrollView.*, **/*Toolbar.*, **/*CardView.*, **/*FloatingActionButton.*, '
                  + '**/*RecyclerView.*, **/*ProgressBar.*, **/*ProgressView.*, **/*Button.*, **/*TabLayout.*, '
                  + '**/*ViewGroup.*, **/*CoordinatorLayout.*, **/*Spinner.*, **/*CheckBox.*, '
                  + '**/*TextView.*, **/*RadioButton.*, **/*SeekBar.*, **/*NavigationView.*, '
                  + '**/*Activity.*, **/*Fragment.*, **/*Screen*, **/*Application.*, '
                  + '**/*Adapter*.*, **/*ViewHolder*.*, **/*Binder.*, **/*BinderImpl.*, '
                  + '**/*ItemDecorator.*, **/*ItemDecoration.*, **/*Snackbar*.*, **/*BaseTransientBottomBar.*'
                  + '**/*Activity$*, **/*Fragment$*, **/*Application$*, '
                  + '**/*Adapter$*, **/*ViewHolder$*, **/*Binder$*, **/*BinderImpl$*, '
                  + '**/*ProgressBar$*, **/*TabLayout$*, **/*RecyclerView$*, '
                  + '**/*ItemDecorator$*, **/*ItemDecoration$*, '
                  // Kotlin specific
                  + '**/*ViewExtensionKt.*, **/*ContextExtensionKt.*, **/*ActivityExtensionKt.*, '
                  + '**/*ExtensionKt*, **/*Companion$CREATOR*, **/*Companion$EMPTY*, '
                  // Project specific
                  + '**/*IntentFactory*, '
                  + '**/*PermissionRequester*',
          execPattern     : '**/build/jacoco/**.exec',
          inclusionPattern: '**/*.class'])
}

void gradleNoDaemon(String tasks) {
    gradle(tasks, '-Dorg.gradle.daemon=false')
}

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if (switches != null) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}