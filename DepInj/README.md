The issue of "[Hilt] @InstallIn, 'value' class is invalid or missing: @dagger.hilt.InstallIn({})"
is solved by the following correction
@InstallIn(SingletonComponent::class)