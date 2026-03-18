dependencies {
    implementation(project(":findaway-data"))
    implementation(project(":findaway-utils"))
    testImplementation(kotlin("test"))
    testImplementation(libs.coroutines.test)
    implementation(libs.coroutines)
}
